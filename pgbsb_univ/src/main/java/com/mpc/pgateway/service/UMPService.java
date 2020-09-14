package com.mpc.pgateway.service;

import org.jpos.iso.ISOMsg;


public interface UMPService {
	public ISOMsg Inquiry(ISOMsg isoMsg) throws Exception;
	public ISOMsg Payment(ISOMsg isoMsg) throws Exception; 
	public ISOMsg Reversal(ISOMsg isoMsg) throws Exception;
}
