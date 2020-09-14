package com.mpc.middleware.service;

import org.springframework.integration.core.MessageHandler;

public interface RawChannelService extends MessageHandler{

	public void sendMessage(String receive);

}
