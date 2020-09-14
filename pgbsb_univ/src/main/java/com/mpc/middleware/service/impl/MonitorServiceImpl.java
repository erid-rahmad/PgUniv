package com.mpc.middleware.service.impl;

import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jpos.iso.ISOMUX;

import com.mpc.middleware.service.MonitorService;

public class MonitorServiceImpl implements MonitorService {
	@Resource(name="configProperties") Map<String,String> config;
	private ISOMUX mdp;
	private ISOMUX ist;
	
	@Override
	public boolean isISTConnected() {
		if(ist != null) return ist.isConnected();
		else return false;
	}
	
	@Override
	public boolean isMDPConnected(){
		if(mdp != null) return mdp.isConnected();
		else return false;
	}
	
	@Override
	public boolean isWSDLConnected(){
		boolean status = false;
		try {
			final SSLContext sslContext = SSLContext.getInstance("TLS");
			 
	        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
	            @Override
	            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
	            }
	 
	            @Override
	            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
	            }
	 
	            @Override
	            public X509Certificate[] getAcceptedIssuers() {
	                return new X509Certificate[0];
	            }
	        }}, null);
	 
	        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        });
	        
			URL url = new URL(config.get("url.wsdl"));
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		    conn.setRequestMethod("GET");
	        int responseCode = conn.getResponseCode();
	        if (responseCode == 200) {
	            status = true;
	        }
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
		}		
		return status;
	}
	/**
	 * @return the mdp
	 */
	public ISOMUX getMdp() {
		return mdp;
	}

	/**
	 * @param mdp the mdp to set
	 */
	public void setMdp(ISOMUX mdp) {
		this.mdp = mdp;
	}

	/**
	 * @return the ist
	 */
	public ISOMUX getIst() {
		return ist;
	}

	/**
	 * @param ist the ist to set
	 */
	public void setIst(ISOMUX ist) {
		this.ist = ist;
	}
	
}
