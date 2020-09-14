package com.mpc.pgateway.service.impl;

import h2humplg.H2HInqury;
import h2humplg.H2HInquryResponse;
import h2humplg.H2HPayment;
import h2humplg.H2HPaymentResponse;
import h2humplg.H2HReversal;
import h2humplg.H2HReversalResponse;
import h2humplg.InquiryResponse;
import h2humplg.ObjectFactory;

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
import com.mpc.pgateway.model.TempInquiry;
import com.mpc.pgateway.service.UMPService;
import com.mpc.pgateway.service.utils.ComposeBit48Ump;

@Service(value="umpService")
public class UMPServiceImpl implements UMPService {
	private Logger log = Logger.getLogger(getClass());
	private static final String RC_TIMEOUT = "08";
	public static final ObjectFactory FACTORYUMP = new ObjectFactory();
	private WebServiceTemplate webServiceTemplate;
	private Map<String, TempInquiry> tempInquiryTagihan = new HashMap<String, TempInquiry>();
	@Value("${password.bank}") private String passwordBank;
	@Value("${kode.bank}") private String kodeBank;
	@Resource(name="rcProperties") Map<String,String> rcProp;
	
	@Autowired
	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
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
		log.debug("~>Compose Inquiry<~");
		H2HInqury req = FACTORYUMP.createH2HInqury();
		try {
			String bit48 = isoMsg.getValue(48).toString();
			Map<String, String> mapBit48 = ComposeBit48Ump.ComposeBit48InquiryRequestToMap(bit48);

			// set value properti inquiry
			req.setKodeBank(kodeBank);
			req.setNomorPembayaran(mapBit48.get(ComposeBit48Ump.NOMORPEMBAYARAN));
			req.setKodeTerminal(isoMsg.getString(ISOMsgBit.TERMID));
			req.setPasswordBank(passwordBank);
			
			req.setTanggalTransaksi(
					new SimpleDateFormat("yyyy").format(new Date())+ 
					isoMsg.getString(ISOMsgBit.TGL2) + 
					isoMsg.getString(ISOMsgBit.JAM));
			req.setKodeChannel(isoMsg.getString(ISOMsgBit.CHANNEL));
			String id = mapBit48.get(ComposeBit48Ump.NOMORPEMBAYARAN).toString()+ isoMsg.getString(ISOMsgBit.TRACE);
			req.setIdTransaksi(id);
			
			//Request and get response
			H2HInquryResponse resp = InquiryWsdl(req);
			String RC = RC_TIMEOUT;
			if(resp != null){
				//reply to IST
				InquiryResponse inqResp = resp.getH2HInquryResult();
				isoMsg.set(ISOMsgBit.AMOUNT,
						ComposeBit48Ump.writeDataEnd(
								inqResp.getTotalNominal(), "0", 
						ComposeBit48Ump.TOTALNOMINAL_LENGTH));
				RC = rcProp.get(inqResp.getCode()).trim();
				isoMsg.set(ISOMsgBit.DATA, mapBit48.get(ComposeBit48Ump.KODEUNIV)+ComposeBit48Ump.ParsingRespInquiryToBit48(inqResp));
				if(RC.equals("00")){
					tempInquiryTagihan.put(getKeyTemp(mapBit48, isoMsg), new TempInquiry(inqResp, new Date()));
				}
				isoMsg.set(ISOMsgBit.ACCTNUM,inqResp.getNomorPembayaran());
			}
			isoMsg.set(ISOMsgBit.RC, RC);
		} catch (ISOException e) {
			e.printStackTrace();
		}
		return isoMsg;
	}
	
	private ISOMsg ComposePayment(ISOMsg isoMsg){
		log.debug("~>Compose Payment<~");
		H2HPayment req = FACTORYUMP.createH2HPayment();
		try {
			String bit48 = isoMsg.getValue(48).toString();
			Map<String, String> mapBit48 = ComposeBit48Ump.ComposeBit48InquiryRequestToMap(bit48);
			String RC = RC_TIMEOUT;
			
			TempInquiry tempInq= tempInquiryTagihan.get(getKeyTemp(mapBit48, isoMsg));
			if (tempInq != null) { // cek data temp
					// prepare properti to payment
					InquiryResponse inquiryResp = tempInq.getInquiryResponse();
					req.setIdTagihan(inquiryResp.getIdTagihan());
					req.setKodeBank(kodeBank);
					req.setPasswordBank(passwordBank);
					req.setKodeChannel(isoMsg.getString(ISOMsgBit.CHANNEL));
					req.setKodeTerminal(isoMsg.getString(ISOMsgBit.TERMID));
					req.setNomorPembayaran(inquiryResp.getNomorPembayaran());
					req.setWaktuTransaksiBank(new SimpleDateFormat("yyyy").format(new Date())+ 
							isoMsg.getString(ISOMsgBit.TGL2) + 
							isoMsg.getString(ISOMsgBit.JAM));
					req.setTotalNominal(inquiryResp.getTotalNominal());
					req.setKodeUnikBank(isoMsg.getString(ISOMsgBit.CHANNEL) + isoMsg.getString(ISOMsgBit.REFNUM));
					req.setNomorJurnalBank(isoMsg.getString(ISOMsgBit.CHANNEL) + isoMsg.getString(ISOMsgBit.REFNUM));
					
					H2HPaymentResponse resp = PaymentWsdl(req);
					
					if(resp != null){
						RC = rcProp.get(resp.getH2HPaymentResult().getCode()).trim();
						isoMsg.set(ISOMsgBit.DATA,mapBit48.get(ComposeBit48Ump.KODEUNIV)+ComposeBit48Ump.ParsingRespPaymentToBit48(inquiryResp));
						if(RC.equals("00")){
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
	private ISOMsg ComposeReversal(ISOMsg isoMsg){
		log.debug("~>Compose Reversal<~");
		H2HReversal req = FACTORYUMP.createH2HReversal();
		try {
			String bit48 = isoMsg.getValue(48).toString();
			Map<String, String> mapBit48 = ComposeBit48Ump.ComposeBit48InquiryRequestToMap(bit48);
			String RC = RC_TIMEOUT;
			TempInquiry temp =tempInquiryTagihan.get(getKeyTemp(mapBit48, isoMsg)); 
			
			if (temp != null) { // cek data temp
					// prepare properti to payment
					InquiryResponse inquiryResp = temp.getInquiryResponse();	
					req.setIdTagihan(inquiryResp.getIdTagihan());
					req.setKodeBank(kodeBank);
					req.setPasswordBank(passwordBank);
					req.setKodeChannel(isoMsg.getString(ISOMsgBit.CHANNEL));
					req.setKodeTerminal(isoMsg.getString(ISOMsgBit.TERMID));
					req.setNomorPembayaran(inquiryResp.getNomorPembayaran());
					req.setWaktuTransaksiBankAsal(new SimpleDateFormat("yyyyMMddhhmmss").format(temp.getInquiryDate()));
					req.setWaktuReversalBank(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
					req.setTotalNominal(inquiryResp.getTotalNominal());
					req.setKodeUnikBank(isoMsg.getString(ISOMsgBit.CHANNEL) + isoMsg.getString(ISOMsgBit.REFNUM));
					
					H2HReversalResponse resp = ReversalWsdl(req);
					if(resp != null){
						RC = rcProp.get(resp.getH2HReversalResult().getCode()).trim();
						isoMsg.set(ISOMsgBit.DATA,mapBit48.get(ComposeBit48Ump.KODEUNIV)+ComposeBit48Ump.ParsingRespPaymentToBit48(inquiryResp));
						if(RC.equals("00")){
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
		return bit48.get(ComposeBit48Ump.NOMORPEMBAYARAN) + isoMsg.getString(ISOMsgBit.TERMID);
	}
	//~WSDL================================================================================
	public H2HInquryResponse InquiryWsdl(H2HInqury inquiry) {
		log.debug("~>Request Inquiry To WSDL<~");
		log.trace("Request: " + inquiry);
		// TODO REQUEST SOAP
		H2HInquryResponse res=null;
		try{
			res = (H2HInquryResponse) webServiceTemplate
					.marshalSendAndReceive(inquiry, new WebServiceMessageCallback() {
						@Override
						public void doWithMessage(WebServiceMessage arg0)
								throws IOException, TransformerException {
							 ((SoapMessage) arg0).setSoapAction("H2hUmplg/H2H-Inqury");
						}
					});
			log.trace("Reponse: " + res.getH2HInquryResult());
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		log.debug("~>End Inquiry From WSDL<~");
		return res;
	}
	
	public H2HPaymentResponse PaymentWsdl(H2HPayment payment) {
		log.debug("~>Request Payment To WSDL<~");
		log.trace("Request: " + payment);
		// TODO REQUEST SOAP
		H2HPaymentResponse res=null;
		try{
			res = (H2HPaymentResponse) webServiceTemplate
					.marshalSendAndReceive(payment, new WebServiceMessageCallback() {
						@Override
						public void doWithMessage(WebServiceMessage arg0)
								throws IOException, TransformerException {
							((SoapMessage) arg0).setSoapAction("H2hUmplg/H2H-Payment");
						}
					});
			log.trace("Reponse: " + res.getH2HPaymentResult());
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		log.debug("~>End Payment From WSDL<~");
		return res;
	}
	
	public H2HReversalResponse ReversalWsdl(H2HReversal reversal) {
		log.debug("~>Request Reversal To WSDL<~");
		log.trace("Request: " + reversal);
		// TODO REQUEST SOAP
		H2HReversalResponse res=null;
		try{
			res = (H2HReversalResponse) webServiceTemplate
					.marshalSendAndReceive(reversal, new WebServiceMessageCallback() {
						@Override
						public void doWithMessage(WebServiceMessage arg0)
								throws IOException, TransformerException {
							((SoapMessage) arg0).setSoapAction("H2hUmplg/H2H-Reversal");
						}
					});

			log.trace("Reponse: " + res.getH2HReversalResult());
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
						if (selisihMenit > 5) { // jika waktu inquiry lebih dari 5 menit -> clear
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