package com.mpc.pgateway.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

public class Creator {
	private Logger log = Logger.getLogger(getClass());
	private final String pattern = "@{app.version}";
	
	@PostConstruct
	public void init() {
		InputStream is = null;
		try {
			is=getClass().getClassLoader().getResourceAsStream("config/data/crea.tor");
			if(is!= null){
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		        String line = new String("");
				while ((line = reader.readLine()) != null) {
					if(line.contains(pattern)) {
						line = line.replace(pattern, ApplicationUtils.getAppVersion());
					}
					log.info(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
