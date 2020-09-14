package com.mpc.pgateway.service.impl;

import id.ac.univpgri_palembang.ws.ClassInquiryRequest;
import id.ac.univpgri_palembang.ws.ClassPaymentRequest;
import id.ac.univpgri_palembang.ws.ClassReversalRequest;
import id.ac.univpgri_palembang.ws.InquiryRespon;
import id.ac.univpgri_palembang.ws.InquiryResponse;
import id.ac.univpgri_palembang.ws.ObjectFactory;
import id.ac.univpgri_palembang.ws.PaymentResponse;
import id.ac.univpgri_palembang.ws.ReversalResponse;
import id.ac.univpgri_palembang.ws.Inquiry;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import com.mpc.iso.util.ISOMsgBit;
import com.mpc.pgateway.model.TempInquiryPGRI;
import com.mpc.pgateway.service.UMPService;
import com.mpc.pgateway.service.utils.ComposeBit48Ump;

@Service(value="pgriService")
public class PGRIServiceImpl implements UMPService {
	private Logger log = Logger.getLogger(getClass());
	private static final String RC_TIMEOUT = "08";
	public static final ObjectFactory FACTORYUMP = new ObjectFactory();
	private WebServiceTemplate webServiceTemplate2;
	private Map<String, TempInquiryPGRI> tempInquiryTagihan = new HashMap<String, TempInquiryPGRI>();
	@Value("${password.bank.pgri}") private String passwordBank;
	@Value("${kode.bank.pgri}") private String kodeBank;
	@Resource(name="rcProperties") Map<String,String> rcProp;
	
	@Autowired
	public void setWebServiceTemplate2(WebServiceTemplate webServiceTemplate2) {
		this.webServiceTemplate2 = webServiceTemplate2;
		new BackgoundWorking().start();
	}
	
	//~> IMPLEMENTASI SERVICE====================================================================
	@Override
	public ISOMsg Inquiry(ISOMsg isoMsg){
		return ComposeInquiry(isoMsg);
	}
	@Override
	public ISOMsg Payment(ISOMsg isoMsg){
		return ComposePayment(isoMsg);
	}
	@Override
	public ISOMsg Reversal(ISOMsg isoMsg){
		return ComposeReversal(isoMsg);
	}
	
	//~> COMPOSE INQUIRY,PAYMENT DAN REVERSAL=====================================================
	private ISOMsg ComposeInquiry(ISOMsg isoMsg){
		log.debug("PGRI ~>Compose Inquiry<~");
		Inquiry req = FACTORYUMP.createInquiry();
		try {
			String bit48 = isoMsg.getValue(48).toString();
			Map<String, String> mapBit48 = ComposeBit48Ump.ComposeBit48InquiryRequestToMap(bit48);

			ClassInquiryRequest inqreq = new ClassInquiryRequest();
			inqreq.setKodeBank(kodeBank);
			inqreq.setNomorPembayaran(bit48.substring(4,17));
			inqreq.setKodeTerminal(isoMsg.getString(ISOMsgBit.TERMID));
			inqreq.setPasswordBank(passwordBank);
			inqreq.setTanggalTransaksi(new SimpleDateFormat("yyyy").format(new Date())+ 
					isoMsg.getString(ISOMsgBit.TGL2) + 
					isoMsg.getString(ISOMsgBit.JAM));
			inqreq.setKodeChannel(isoMsg.getString(ISOMsgBit.CHANNEL));
			String id = mapBit48.get(ComposeBit48Ump.NOMORPEMBAYARAN).toString()+ isoMsg.getString(ISOMsgBit.TRACE);
			inqreq.setIdTransaksi(id);
			
			req.setInquiryRequest(inqreq);
			
			//Request and get response
			InquiryResponse resp = InquiryWsdl(req);
			String RC = RC_TIMEOUT;
			if(resp != null){
				//reply to IST
				InquiryRespon inqResp = resp.getInquiryResult();
				isoMsg.set(ISOMsgBit.AMOUNT,
						ComposeBit48Ump.writeDataEnd(
								inqResp.getTotalNominal(), "0", 
						ComposeBit48Ump.TOTALNOMINAL_LENGTH));
				log.debug("Application Fault - Code "+resp.getInquiryResult().getApplicationfault().getCode());
				String rccode = rcProp.get(inqResp.getApplicationfault().getCode());
				if(rccode != null){
					RC = rccode.trim();						
				}else{	
					log.error("ERROR, not defined");
					RC = "12";
				}
				
				log.trace("RC :"+RC);
				log.trace("amount: "+inqResp.getTotalNominal());
				
				//transaksi PGRI
				if(RC.equals("00")){
//					mapBit48.put(ComposeBit48Ump.NOMORPEMBAYARAN,inqResp.getDataMahasiswa().getNomorInduk().trim()); //saat payment dari channel, bit48 akan dikirimkan no pembayaran.
					isoMsg.set(ISOMsgBit.DATA, mapBit48.get(ComposeBit48Ump.KODEUNIV)+ComposeBit48Ump.ParsingRespInquiryToBit48PGRI(inqResp));
					tempInquiryTagihan.put(getKeyTemp(mapBit48, isoMsg), new TempInquiryPGRI(inqResp, new Date()));
					if(inqResp.getNomorPembayaran() != null){
						log.info("put key: "+inqResp.getNomorPembayaran()+ isoMsg.getString(ISOMsgBit.TERMID));
						tempInquiryTagihan.put(inqResp.getNomorPembayaran().trim()+ isoMsg.getString(ISOMsgBit.TERMID), new TempInquiryPGRI(inqResp, new Date()));  //put juga dgn no pembayaran. otomatis dihapus oleh thread hapus
					}
					if(inqResp.getDataMahasiswa() != null){
						if(inqResp.getDataMahasiswa().getNomorInduk() != null){
							log.info("put key: "+ inqResp.getDataMahasiswa().getNomorInduk()+ isoMsg.getString(ISOMsgBit.TERMID));
							tempInquiryTagihan.put(inqResp.getDataMahasiswa().getNomorInduk().trim()+ isoMsg.getString(ISOMsgBit.TERMID), new TempInquiryPGRI(inqResp, new Date()));  //put juga dgn no pembayaran. otomatis dihapus oleh thread hapus
						}
					}
				}
				isoMsg.set(ISOMsgBit.ACCTNUM,inqResp.getDataMahasiswa().getNomorInduk());
			}
			isoMsg.set(ISOMsgBit.RC, RC);
		} catch (ISOException e) {
			e.printStackTrace();
		}
		return isoMsg;
	}
	
	private ISOMsg ComposePayment(ISOMsg isoMsg){
		log.debug("~>Compose Payment<~");
		id.ac.univpgri_palembang.ws.Payment req = FACTORYUMP.createPayment();
		try {
			String bit48 = isoMsg.getValue(48).toString();
			Map<String, String> mapBit48 = ComposeBit48Ump.ComposeBit48InquiryRequestToMap(bit48);
			String RC = RC_TIMEOUT;

			TempInquiryPGRI tempInq= tempInquiryTagihan.get(getKeyTemp(mapBit48, isoMsg));
			for(String s : tempInquiryTagihan.keySet()){
				log.info(s);
			}
			if (tempInq != null) { // cek data temp
				// prepare properti to payment
				InquiryRespon inquiryResp = tempInq.getInquiryRespon();
				
				ClassPaymentRequest payreq = new ClassPaymentRequest();
//				req.setIdTagihan(inquiryResp.getIdTagihan());
				payreq.setKodeBank(kodeBank);
				payreq.setPasswordBank(passwordBank);
				payreq.setKodeChannel(isoMsg.getString(ISOMsgBit.CHANNEL));
				payreq.setKodeTerminal(isoMsg.getString(ISOMsgBit.TERMID));
				payreq.setNomorPembayaran(inquiryResp.getNomorPembayaran());
				payreq.setTanggalTransaksi(new SimpleDateFormat("yyyy").format(new Date())+ 
						isoMsg.getString(ISOMsgBit.TGL2) + 
						isoMsg.getString(ISOMsgBit.JAM));
				payreq.setTotalNominal(inquiryResp.getTotalNominal());
				payreq.setNomorJurnalPembukuan(isoMsg.getString(ISOMsgBit.CHANNEL) + isoMsg.getString(ISOMsgBit.REFNUM));

				req.setPaymentRequest(payreq);
				
				PaymentResponse resp = PaymentWsdl(req);

				if(resp != null){
					log.debug("Application Fault - Code "+resp.getPaymentResult().getApplicationfault().getCode());
					String rccode = rcProp.get(resp.getPaymentResult().getApplicationfault().getCode());
					if(rccode != null){
						RC = rccode.trim();						
					}else{	
						log.error("ERROR, not defined");
						RC = "12";
					}
					if(RC.equals("00")){
						isoMsg.set(ISOMsgBit.DATA,mapBit48.get(ComposeBit48Ump.KODEUNIV)+ComposeBit48Ump.ParsingRespPaymentToBit48PGRI(inquiryResp));
						tempInquiryTagihan.remove(getKeyTemp(mapBit48, isoMsg));
					}
					isoMsg.set(ISOMsgBit.ACCTNUM,resp.getPaymentResult().getDataMahasiswa().getNomorInduk());
				}

			} else {
				RC = rcProp.get("1");
				System.out.println("Data transaksi inquiry tagihan tidak ditemukan");
				log.error("Data transaksi inquiry tagihan tidak ditemukan");
			}
			isoMsg.set(ISOMsgBit.RC, RC);
		} catch (ISOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isoMsg;
	}
	private ISOMsg ComposeReversal(ISOMsg isoMsg){
		log.debug("~>Compose Reversal<~");
		id.ac.univpgri_palembang.ws.Reversal req = FACTORYUMP.createReversal();
		try {
			String bit48 = isoMsg.getValue(48).toString();
			Map<String, String> mapBit48 = ComposeBit48Ump.ComposeBit48InquiryRequestToMap(bit48);
			String RC = RC_TIMEOUT;
			TempInquiryPGRI temp =tempInquiryTagihan.get(getKeyTemp(mapBit48, isoMsg)); 

			//process reversal
			if (temp != null) { // cek data temp
				// prepare properti to payment
				InquiryRespon inquiryResp = temp.getInquiryRespon();	
//				req.setIdTagihan(inquiryResp.getIdTagihan());
				
				ClassReversalRequest revreq = new ClassReversalRequest();
				revreq.setKodeBank(kodeBank);
				revreq.setPasswordBank(passwordBank);
				revreq.setKodeChannel(isoMsg.getString(ISOMsgBit.CHANNEL));
				revreq.setKodeTerminal(isoMsg.getString(ISOMsgBit.TERMID));
				revreq.setNomorPembayaran(inquiryResp.getNomorPembayaran());
				revreq.setTanggalTransaksiAsal((new SimpleDateFormat("yyyyMMddhhmmss").format(temp.getInquiryDate())));
				revreq.setTanggalTransaksi(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
				revreq.setTotalnominal(inquiryResp.getTotalNominal());
//				revreq.setKodeUnikBank(isoMsg.getString(ISOMsgBit.CHANNEL) + isoMsg.getString(ISOMsgBit.REFNUM));
				
				req.setReversalRequest(revreq);

				ReversalResponse resp = ReversalWsdl(req);
				if(resp != null){
					log.debug("Application Fault - Code "+resp.getReversalResult().getApplicationfault().getCode());
					String rccode = rcProp.get(resp.getReversalResult().getApplicationfault().getCode());
					if(rccode != null){
						RC = rccode.trim();						
					}else{	
						log.error("ERROR, not defined");
						RC = "12";
					}
					if(RC.equals("00")){
						isoMsg.set(ISOMsgBit.DATA,mapBit48.get(ComposeBit48Ump.KODEUNIV)+ComposeBit48Ump.ParsingRespPaymentToBit48PGRI(inquiryResp));
						tempInquiryTagihan.remove(getKeyTemp(mapBit48, isoMsg));
					}
				}

			} else {
				RC = rcProp.get("1");
				System.out.println("Data transaksi inquiry tagihan tidak ditemukan");
			}
			isoMsg.set(ISOMsgBit.RC, RC);
		} catch (ISOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isoMsg;
	}
	
	//~FUNCTION============================================================================
	private String getKeyTemp(Map<String,String> bit48,ISOMsg isoMsg){
		String nop = bit48.get(ComposeBit48Ump.NOMORPEMBAYARAN);
		String key = nop + isoMsg.getString(ISOMsgBit.TERMID);
		log.debug("get key: "+key);
		return key;
	}
	//~WSDL================================================================================
	public InquiryResponse InquiryWsdl(Inquiry inquiry) {
		log.debug("~>Request Inquiry To WSDL<~");
		log.trace("Request: " + inquiry.getInquiryRequest().toString() + "to" +webServiceTemplate2.getDefaultUri());
		
		// TODO REQUEST SOAP Action
		InquiryResponse res=null;
		try{
			res = (InquiryResponse) webServiceTemplate2
					.marshalSendAndReceive(inquiry, new WebServiceMessageCallback() {
						@Override
						public void doWithMessage(WebServiceMessage arg0)
								throws IOException, TransformerException {
							 ((SoapMessage) arg0).setSoapAction("http://ws.univpgri-palembang.ac.id/inquiry");
						}
					});
			log.trace("Reponse : " + res.getInquiryResult().toString());
			log.trace("NIM : " + res.getInquiryResult().getDataMahasiswa().getNomorInduk());
			log.trace("ID Mahasiswa : " + res.getInquiryResult().getNomorPembayaran());
			log.trace("Periode / Angkatan: " + res.getInquiryResult().getDataMahasiswa().getPeriode().toString() + res.getInquiryResult().getDataMahasiswa().getAngkatan().toString());
			
			}catch(Exception e){
			log.debug("error",e);
		}
		log.debug("~>End Inquiry From WSDL<~");
		return res;
	}
	
	public PaymentResponse PaymentWsdl(id.ac.univpgri_palembang.ws.Payment payment) {
		log.debug("~>Request Payment To WSDL<~");
		log.trace("Request: " + payment);
		// TODO REQUEST SOAP
		PaymentResponse res=null;
		try{
			res = (PaymentResponse) webServiceTemplate2
					.marshalSendAndReceive(payment, new WebServiceMessageCallback() {
						@Override
						public void doWithMessage(WebServiceMessage arg0)
								throws IOException, TransformerException {
							((SoapMessage) arg0).setSoapAction("http://ws.univpgri-palembang.ac.id/payment");
						}
					});
			log.trace("Reponse: " + res.getPaymentResult());
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		log.debug("~>End Payment From WSDL<~");
		return res;
	}
	
	public ReversalResponse ReversalWsdl(id.ac.univpgri_palembang.ws.Reversal reversal) {
		log.debug("~>Request Reversal To WSDL<~");
		log.trace("Request: " + reversal);
		// TODO REQUEST SOAP
		ReversalResponse res=null;
		try{
			res = (ReversalResponse) webServiceTemplate2
					.marshalSendAndReceive(reversal, new WebServiceMessageCallback() {
						@Override
						public void doWithMessage(WebServiceMessage arg0)
								throws IOException, TransformerException {
							((SoapMessage) arg0).setSoapAction("http://ws.univpgri-palembang.ac.id/reversal");
						}
					});

			log.trace("Reponse: " + res.getReversalResult());
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		log.debug("~>End Reversal From WSDL<~");
		return res;
	}
	
	// thread untuk menghapus temporary inquiry
	private class BackgoundWorking implements Runnable {
		private Thread t;

		public void run() {
			try {
				while (true) {
					for (String key : tempInquiryTagihan.keySet()) {
						Date dt = tempInquiryTagihan.get(key).getInquiryDate();
						long selisihMS = Math.abs(new Date().getTime() - dt.getTime());
						//long selisihDetik = selisihMS / 1000 % 60;
						long selisihMenit = selisihMS / (60 * 1000) % 60;
						if (selisihMenit > 20) { // jika waktu inquiry lebih dari 5 menit -> clear
							System.out.println("Clear temp id inquiry: "+ key);
							tempInquiryTagihan.remove(key);
						}
					}
					Thread.sleep(1000); // setiap 1 detik
				}
			} catch (InterruptedException e) {
				System.out.println("Thread clear  interrupted.");
			}
		}

		public void start() {
			if (t == null) {
				t = new Thread(this);
				t.start();
			}
		}
	}
}