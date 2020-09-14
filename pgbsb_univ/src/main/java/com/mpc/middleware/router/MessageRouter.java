package com.mpc.middleware.router;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class MessageRouter {
	
	private static final Logger log = LoggerFactory.getLogger(MessageRouter.class);
	
	@Value("${bsbUnivOthers.prefix}")
	private String bsbUnivOthersPrefix;
	
	@Value("${ist.connection.prefix}")
	private String istPrefix;
	
	public MessageRouter() {

	}
		
	public String routeIstInBound(ISOMsg isomsg) throws ISOException {
		if ("0800".equals(isomsg.getMTI())) {
			isomsg.setResponseMTI();
			isomsg.set(39, "00");
		} else if ("0810".equals(isomsg.getMTI())) {
			return "mdp-outbound-channel";
		}else {
			//define destination channel berdasarkan prefix		
			log.info(isomsg.getString(48));
			if(bsbUnivOthersPrefix != null){
				String prefix[] = bsbUnivOthersPrefix.split(";");
				
				Object f48 = isomsg.getValue(48);
				if(f48 != null){
					for(int i=0;i<prefix.length;i++){
						if(f48.toString().startsWith(prefix[i])){
							log.debug("got prefix "+prefix[i]+", reroute to mdp (old PG)");
							return "mdp-outbound-channel";
						}
					}
				}
			}
			
			if(istPrefix != null){
				String prefix[] = istPrefix.split(";");
				
				Object f48 = isomsg.getValue(48);
				if(f48 != null){
					for(int i=0;i<prefix.length;i++){
						if(f48.toString().startsWith(prefix[i])){
							
							log.debug("got prefix "+prefix[i]+", reroute to ump, pgri, umpp, stikes");
							
							return "ist-inbound-channel";
						}
					}
				}
			}
			
			//set response
			if(isomsg.isRequest()){
				isomsg.setResponseMTI();
				isomsg.set(39, "14");
			}
		}

		return "ist-outbound-channel";
	}
}
