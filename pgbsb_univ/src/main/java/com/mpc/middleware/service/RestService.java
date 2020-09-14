package com.mpc.middleware.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestService {
	
	private WebResource webResource;
	private String path;
	
	public WebResource getWebResource() {
		if(webResource == null){
			webResource = create(path);
		}
		return webResource;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static WebResource create(String path){
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client c = Client.create(cfg);
		
		WebResource r = c.resource(path);
		
		return r;
	}
}
