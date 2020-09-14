package com.mpc.connection.tcpip;

import java.util.EventObject;

public class ReceiverEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7782529919071016808L;
	private String receive;
	public ReceiverEvent(Object source, String receive) {
		super(source);
		this.receive = receive;
	}
	
	public String getReceive() {
		return receive;
	}
}
