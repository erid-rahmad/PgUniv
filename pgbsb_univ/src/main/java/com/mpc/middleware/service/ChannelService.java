package com.mpc.middleware.service;

import org.jpos.iso.ISOMsg;
import org.springframework.integration.core.MessageHandler;

public interface ChannelService extends MessageHandler {
	
	public void sendMsg(ISOMsg payload);

	public void handleMsg(ISOMsg payload);

	
}
