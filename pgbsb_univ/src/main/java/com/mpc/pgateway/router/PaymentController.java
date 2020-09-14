package com.mpc.pgateway.router;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;

import com.mpc.iso.util.ISOMsgBit;
import com.mpc.middleware.service.ChannelService;
import com.mpc.pgateway.service.UMPService;
import com.mpc.pgateway.service.utils.KeyParam;
import com.mpc.pgateway.service.utils.RcUtils;
import com.mpc.pgateway.service.utils.StandartUtils;

@Controller
public class PaymentController implements ChannelService  {
	private Logger log = Logger.getLogger(getClass());
	private MessageChannel sendChannel;
	@Resource(name="pcodeProperties") Map<String,String> pcodeProp;
	
	@Autowired private UMPService umpService;
	@Autowired private UMPService pgriService;
	@Autowired private UMPService umppService;
	@Autowired private UMPService stikesService;
	
	private ISOMsg SwitchUnivInquiry(ISOMsg isoMsg) throws Exception{
		int kodeUniv = Integer.parseInt(isoMsg.getString(48).substring(0,4));
		log.debug("Univ code: "+kodeUniv);
		if(kodeUniv == UNIV_CODE.UMP.code)
			isoMsg = this.umpService.Inquiry(isoMsg); 
		else if(kodeUniv == UNIV_CODE.PGRI.code)
			isoMsg = pgriService.Inquiry(isoMsg);
		else if(kodeUniv == UNIV_CODE.UMPP.code)
			isoMsg = this.umppService.Inquiry(isoMsg);
		else if(kodeUniv == UNIV_CODE.STIKES.code)
			isoMsg = stikesService.Inquiry(isoMsg);		
		return isoMsg;
	}
	
	private ISOMsg SwitchUnivPayment(ISOMsg isoMsg) throws Exception{
		int kodeUniv = Integer.parseInt(isoMsg.getString(48).substring(0,4));
		log.debug("Univ code: "+kodeUniv);
		if(kodeUniv == UNIV_CODE.UMP.code)
			isoMsg = this.umpService.Payment(isoMsg); 
		else if(kodeUniv == UNIV_CODE.PGRI.code)
			isoMsg = pgriService.Payment(isoMsg);
		else if(kodeUniv == UNIV_CODE.UMPP.code)
			isoMsg = this.umppService.Payment(isoMsg);
		else if(kodeUniv == UNIV_CODE.STIKES.code)
			isoMsg = stikesService.Payment(isoMsg);

		return isoMsg;
	}
	
	private ISOMsg SwitchUnivReversal(ISOMsg isoMsg) throws Exception{
		int kodeUniv = Integer.parseInt(isoMsg.getString(48).substring(0,4));
		log.debug("Univ code: "+kodeUniv);
		if(kodeUniv == UNIV_CODE.UMP.code)
			isoMsg = this.umpService.Reversal(isoMsg); 
		else if(kodeUniv == UNIV_CODE.PGRI.code)
			isoMsg = pgriService.Reversal(isoMsg);
		else if(kodeUniv == UNIV_CODE.UMPP.code)
			isoMsg = this.umppService.Reversal(isoMsg);
		else if(kodeUniv == UNIV_CODE.STIKES.code)
			isoMsg = stikesService.Reversal(isoMsg);

		return isoMsg;
	}
		
	//~> HANDLE ISO MESSAGE
	@Override
	public void handleMsg(ISOMsg isoMsg) {
		try {
			String mti = isoMsg.getMTI(); // message type
			Integer pcode = StandartUtils.getPcode(isoMsg.getString(ISOMsgBit.PCODE));
			isoMsg.set(ISOMsgBit.RC,"05");
			if (mti.equals("0200")) {
				if (this.pcodeProp.get(KeyParam.PCODE_INQ).equals(pcode.toString())) {
					isoMsg = SwitchUnivInquiry(isoMsg);
				} else if (this.pcodeProp.get(KeyParam.PCODE_EXE).equals(pcode.toString())) {
					isoMsg = SwitchUnivPayment(isoMsg);
				}
			} else if (mti.equals("0400") || mti.equals("0420")) {
				if (this.pcodeProp.get(KeyParam.PCODE_EXE).equals(pcode.toString())) {
					isoMsg = SwitchUnivReversal(isoMsg);
				}
			}
			isoMsg.setResponseMTI();
		} catch (Exception e) {
			log.error("ERROR",e);
			try {
				if(isoMsg.isRequest()) {
					isoMsg.setResponseMTI();
					isoMsg.set(39, RcUtils.MESSAGE_FORMAT_ERROR);
				}
			} catch (ISOException e1) {
				log.error("ERROR",e);
			}
		}
		sendMsg(isoMsg);
	}
	
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		ISOMsg msg = (ISOMsg) message.getPayload();
		handleMsg(msg);
	}
	
	@Override
	public void sendMsg(ISOMsg payload) {
		Message<ISOMsg> message = MessageBuilder.withPayload(payload).build();
		sendChannel.send(message);		
	}
	
	//~> SETTER GETTER
	public MessageChannel getSendChannel() {
		return sendChannel;
	}

	public void setSendChannel(MessageChannel sendChannel) {
		this.sendChannel = sendChannel;
	}
	
}
