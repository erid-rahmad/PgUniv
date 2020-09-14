package com.mpc.middleware.processor;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreProcessor {
	private static final Logger log = LoggerFactory
			.getLogger(CoreProcessor.class);
		
	public CoreProcessor() {
	}
	public ISOMsg processIstToCore(ISOMsg isomsg)
			throws NumberFormatException, ISOException {
		log.info("Process IST to Core: t"+isomsg.getString(11));
		
		return isomsg;
	}


	public ISOMsg processCoreToIst(ISOMsg isomsg)
			throws NumberFormatException, ISOException {
		log.info("Process Core to IST: t"+isomsg.getString(11));
		return isomsg;
	}
}
