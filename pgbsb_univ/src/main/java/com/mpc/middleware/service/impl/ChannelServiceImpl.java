package com.mpc.middleware.service.impl;

import org.jpos.iso.ISOMUX;
import org.jpos.iso.ISOMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.support.MessageBuilder;

import com.mpc.iso.util.Tracer;
import com.mpc.middleware.service.ChannelService;

public class ChannelServiceImpl implements ChannelService {
	
	private static final Logger log = LoggerFactory
			.getLogger(ChannelServiceImpl.class);
	
	private MessageChannel sendChannel;
	private PollableChannel receiveChannel;
	private ISOMUX mux;
	
	@Value("${poller.timeout}")
	private int timeout;

	public void sendMsg(ISOMsg payload) {
		log.trace("-> Message inbound - channel");
		Tracer.printRawMessage(mux.getISOChannel().getName(), payload, true);
		Message<ISOMsg> message = MessageBuilder.withPayload(payload).build();
		sendChannel.send(message);
	}

	public void handleMsg(ISOMsg payload) {
		log.trace("-> Message outbound - channel");
		Tracer.printRawMessage(mux.getISOChannel().getName(), payload, true);
		if (payload != null) {
			mux.send(payload);				
		} else {
			log.error("Null Txn Msg");
		}
	}
	
	public MessageChannel getSendChannel() {
		return sendChannel;
	}

	public void setSendChannel(MessageChannel sendChannel) {
		this.sendChannel = sendChannel;
	}

	public PollableChannel getReceiveChannel() {
		return receiveChannel;
	}

	public void setReceiveChannel(PollableChannel receiveChannel) {
		this.receiveChannel = receiveChannel;
	}

	public void setMux(ISOMUX mux) {
		this.mux = mux;
	}
	
	public void handleMessage(Message<?> message) throws MessagingException {
		handleMsg((ISOMsg)message.getPayload());
	}


}
