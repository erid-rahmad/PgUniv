package com.mpc.middleware.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class NetworkMsgGenerator {
	
	public static final String ECHO = "301";
	public static final String SIGNON = "001";
	public static final String SIGNOFF = "002";
	
	private String netMsgType = "301";
	
	public NetworkMsgGenerator (String type) {
		this.netMsgType = type;
	}
	
	public ISOMsg generate() throws ISOException {
		return generateMSG(this.netMsgType);
	}
	
	public ISOMsg generateMSG(String netMsgType) throws ISOException {
		ISOMsg msg = new ISOMsg();
		
		msg.setMTI("0800");
		msg.set(0, "0800");
		
		SimpleDateFormat dateFormatGMT = new SimpleDateFormat("MMddHHmmss");
		dateFormatGMT.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		
		Date date = new Date();
		
		msg.set(7, dateFormatGMT.format(date));
		
		long milis = System.currentTimeMillis() % 1000000;
		
		msg.set(11, String.format("%06d", milis));
		
		msg.set(70, netMsgType);
		
		return msg;
	}
	
	public ISOMsg sendEcho(String type) throws ISOException {
		return generateMSG(type);
	}
	
	public ISOMsg generateEcho(ISOMsg payload) throws ISOException {
		return generateMSG(ECHO);
	}
	
	public ISOMsg generateSignon(ISOMsg payload) throws ISOException {
		return generateMSG(SIGNON);
	}
	
	public ISOMsg generateSignoff(ISOMsg payload) throws ISOException {
		return generateMSG(SIGNOFF);
	}
	
}
