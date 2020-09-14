package com.mpc.middleware.service.impl;

import java.io.IOException;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.support.MessageBuilder;

import com.mpc.connection.tcpip.Client2;
import com.mpc.middleware.service.RawChannelService;

public class RawChannelServiceImpl implements RawChannelService{
	
	private MessageChannel sendChannel;
	private PollableChannel receiveChannel;
	
	private Client2 client2;

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			client2.send((String)message.getPayload());
		} catch (IOException e) {
			e.printStackTrace();
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

	public void setClient2(Client2 client2) {
		this.client2 = client2;
	}

	@Override
	public void sendMessage(String receive) {
		Message<String> message = MessageBuilder.withPayload(receive).build();
		sendChannel.send(message);
	}

}
