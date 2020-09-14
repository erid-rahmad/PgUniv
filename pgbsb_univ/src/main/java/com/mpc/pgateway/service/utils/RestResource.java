package com.mpc.pgateway.service.utils;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestResource {
	
	private WebResource webResource;
	private String path;
	private static int timeout=0;
	public WebResource getWebResource() {
		if(webResource == null){
			webResource = create(path);
		}
		return webResource;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public static WebResource create(String path){
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client c = Client.create(cfg);
		c.setReadTimeout(timeout);
		WebResource r = c.resource(path);
		
		return r;
	}
}
