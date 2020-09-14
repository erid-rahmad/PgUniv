package com.mpc.iso;

import java.util.Date;

import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

public class Generator {

	public static ISOMsg createSignon() throws ISOException{
		Date d = new Date();
		
		//TODO sequence trace
		long mls = d.getTime();
		long trace = mls % 1000000;
		String traceNumber = ISOUtil.zeropad(String.valueOf(trace), 6);
		
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setMTI("0800");
		isoMsg.set(0, "0800");
		isoMsg.set(new ISOField(7, ISODate.getDateTime(d)));		
//					isoMsg.set(new ISOField(18, "6014")); //VLINK Purposes
		isoMsg.set(new ISOField(11, traceNumber));
		isoMsg.set(new ISOField(70, "001" )); 
		
		return isoMsg;
	}
}
