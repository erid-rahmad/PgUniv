package com.mpc.connection.tcpip;

import com.mpc.middleware.service.RawChannelService;


public class ClientHandlerListener implements ReceiverListener {

	private RawChannelService rawChannelService;
		
	public ClientHandlerListener( RawChannelService rawChannelService) {
		this.rawChannelService = rawChannelService;
	}



	@Override
	public void receive(ReceiverEvent evt) {
		rawChannelService.sendMessage(evt.getReceive());
	}

}
