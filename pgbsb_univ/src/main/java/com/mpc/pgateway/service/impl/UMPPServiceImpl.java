package com.mpc.pgateway.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mpc.pgateway.model.UMPPBaseResponse;
import com.mpc.pgateway.model.UMPPIdentity;
import com.mpc.pgateway.model.UMPPInquiryRequest;
import com.mpc.pgateway.model.UMPPInquiryResponse;
import com.mpc.pgateway.model.UMPPPaymentRequest;
import com.mpc.pgateway.model.UMPPPaymentResponse;
import com.mpc.pgateway.model.UMPPReversalRequest;
import com.mpc.pgateway.service.UMPService;
import com.mpc.pgateway.service.utils.ComposeDataUMPP;
import com.mpc.pgateway.service.utils.RcUtils;
import com.mpc.pgateway.service.utils.RestResource;
import com.mpc.pgateway.service.utils.StandartUtils;

@Service(value="umppService")
public class UMPPServiceImpl extends ComposeDataUMPP implements UMPService {
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	RestResource umppRestResource;
	
	@Resource(name = "channelProperties")
	private Map<String, String> chProp;
	@Resource(name="rcProperties")
	Map<String,String> rcProp;
	
	@Value("${kode.bank.umpp}")
	private String kodeBank;
	@Value("${password.bank.umpp}")
	private String passwordBank;
	
	public UMPPIdentity getIdentity() {
		return new UMPPIdentity(kodeBank, passwordBank);
	}
	
	@Override
	public ISOMsg Inquiry(ISOMsg isoMsg) throws ISOException {
		log.debug("Inquiry " + isoMsg.getString(11));
		
		isoMsg.set(39,RcUtils.ISSUER_TIMEOUT);
		
		UMPPIdentity id = getIdentity();
		UMPPInquiryRequest inq = new UMPPInquiryRequest();
		
		inq.setKodeBank(id.getKodeBank());
		inq.setPasswordBank(id.getPasswordBank());
		inq.setKodeChannel(chProp.get(isoMsg.getString(18)));
		inq.setIdTransaksi(isoMsg.getString(37));
		inq.setKodeTerminal(isoMsg.getString(41));
		inq.setTanggalTransaksi(StandartUtils.format_yyyyMMddHHmmss.format(StandartUtils.getISOLocalDateTime(isoMsg)));
		
		inq.setNomorPembayaran(getNomorPembayaran(isoMsg.getString(48)));
		log.trace(inq);
		
		UMPPInquiryResponse resp = null;
		try {
			resp = umppRestResource.getWebResource()
					.path("inquiry")
					.queryParams(inq.convertToMap())
					.get(UMPPInquiryResponse.class);
			
		}catch(Exception e) {
			log.error("",e);
		}
		
		log.trace(resp);
		if(resp != null) {
			isoMsg.set(4,resp.getTotalNominal());
			isoMsg.set(39,mappingRc(resp.getCode()));
			if(Integer.parseInt(resp.getCode()) == 0) {
				isoMsg.set(48, composeDataResponseInqToIST(resp));
				addMemory(resp.getIdTagihan(),isoMsg);
			}
		}
		return isoMsg;
	}

	@Override
	public ISOMsg Payment(ISOMsg isoMsg) throws ISOException {
		log.debug("Payment " + isoMsg.getString(11));
		isoMsg.set(39,RcUtils.ISSUER_TIMEOUT);
		
		UMPPIdentity id = getIdentity();
		UMPPPaymentRequest pay = new UMPPPaymentRequest();
		String temp[] = getMemory(isoMsg);
		/* add to temporary data request payment for response payment */
		pay.setKodeBank(id.getKodeBank());
		pay.setPasswordBank(id.getPasswordBank());
		pay.setKodeChannel(chProp.get(isoMsg.getString(18)));
		pay.setWaktuTransaksiBank(StandartUtils.format_yyyyMMddHHmmss.format(StandartUtils.getISOLocalDateTime(isoMsg)));
		pay.setKodeUnikBank(isoMsg.getString(37));
		pay.setNomorJunalBank(isoMsg.getString(37));

		pay.setNomorPembayaran(getNomorPembayaran(isoMsg.getString(48)));
		pay.setIdTagihan(temp[0]);
		pay.setTotalNominal(new BigDecimal(isoMsg.getString(4))+"");
		log.trace(pay);
		
		UMPPPaymentResponse resp = null;
		try {
			resp = umppRestResource.getWebResource()
					.path("payment")
					.queryParams(pay.convertToMap())
					.get(UMPPPaymentResponse.class);
		}catch(Exception e) {
			log.error("",e);
		}
		
		log.trace(resp);
		if(resp != null) {
			isoMsg.set(4,resp.getTotalNominal());
			isoMsg.set(48,composeDataResponsePayToIST(temp[1]));
			isoMsg.set(39,mappingRc(resp.getCode()));
			//clear temporary by id
			removeMemory(isoMsg);
		}
		return isoMsg;
	}

	@Override
	public ISOMsg Reversal(ISOMsg isoMsg) throws ISOException {
		log.debug("Reversal " + isoMsg.getString(11));
		isoMsg.set(39,RcUtils.ISSUER_TIMEOUT);
		
		String temp[] = getMemory(isoMsg);
		UMPPIdentity id = getIdentity();
		UMPPReversalRequest req = new UMPPReversalRequest();

		req.setKodeBank(id.getKodeBank());
		req.setPasswordBank(id.getPasswordBank());
		
		req.setIdTagihan(temp[0]);
		req.setWaktuReversal(StandartUtils.format_yyyyMMddHHmmss.format(StandartUtils.getISOLocalDateTime(isoMsg)));
		log.trace(req);
		
		UMPPBaseResponse resp = null;
		try {
			resp = umppRestResource.getWebResource()
					.path("reversal")
					.queryParams(req.convertToMap())
					.get(UMPPBaseResponse.class);
		}catch(Exception e) {
			log.error("",e);
		}
		
		log.trace(resp);
		if(resp != null) {
			isoMsg.set(39,mappingRc(resp.getCode()));
			isoMsg.set(48,temp[1]);
			//clear temporary by id
			removeMemory(isoMsg);
		}
		
		return isoMsg;
	}
	
	private String mappingRc(String respcode) {
		String rc = rcProp.get(respcode);
		return rc == null ? respcode : rc;
	}
}
