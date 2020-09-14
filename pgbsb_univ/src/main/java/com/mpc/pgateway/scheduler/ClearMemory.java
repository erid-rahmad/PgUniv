package com.mpc.pgateway.scheduler;

import org.apache.log4j.Logger;

import com.mpc.pgateway.service.utils.ComposeDataUMPP;

public class ClearMemory {
	private Logger log = Logger.getLogger(getClass());
	
	public void umpp() {
		ComposeDataUMPP.clearMemory();
		log.debug("Clear memory samsat...");
	}
}
