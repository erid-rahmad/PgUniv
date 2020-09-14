package com.mpc.iso;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpc.middleware.service.ChannelService;
import com.mpc.pgateway.service.utils.RcUtils;

public class GerbangListener implements ISORequestListener{
	
	private static final Logger log = LoggerFactory.getLogger(GerbangListener.class);
	private ChannelService channelService;
	private Gerbang gerbang;
	
	public void setGerbang(Gerbang gerbang) {
		this.gerbang = gerbang;
	}

	public GerbangListener(ChannelService channelService) {
		this.channelService = channelService;
	}
	
	public boolean process(ISOSource arg0, ISOMsg isoMsg) {
		try {
			channelService.sendMsg(isoMsg);
			
			if(isoMsg.getMTI().equals("0810") && isoMsg.hasField(70) 
					&& isoMsg.getValue(70).equals("001")){
				log.debug("signon is succesfull");
				gerbang.setSignedOn(true);
			}
		} catch (ISOException e) {
			log.error("Error parsing ISO Message : ", e);
			try {
				isoMsg.set(39, RcUtils.MESSAGE_FORMAT_ERROR);
				if(isoMsg.isRequest())
					isoMsg.setResponseMTI();
			} catch (ISOException e1) {
				log.error(e1.getMessage());
			}
			channelService.handleMsg(isoMsg);
		}
		return false;
	}

}
