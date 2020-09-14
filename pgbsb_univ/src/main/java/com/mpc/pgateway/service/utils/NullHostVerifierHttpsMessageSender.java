package com.mpc.pgateway.service.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
 
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;
 
public class NullHostVerifierHttpsMessageSender extends
        HttpsUrlConnectionMessageSender {
 
    private NullHostnameVerifier _hostnameVerifier;
    private SSLSocketFactory _sslSocketFactory;
 
    public NullHostVerifierHttpsMessageSender() throws NoSuchAlgorithmException,
            KeyManagementException {
 
        _hostnameVerifier = new NullHostnameVerifier();
 
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
 
            public void checkClientTrusted(X509Certificate[] certs,
                    String authType) {
            }
 
            public void checkServerTrusted(X509Certificate[] certs,
                    String authType) {
            }
        } };
 
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        _sslSocketFactory = sc.getSocketFactory();
 
    }
 
    @Override
    public void afterPropertiesSet() throws Exception {
        setHostnameVerifier(_hostnameVerifier);
        setSslSocketFactory(_sslSocketFactory);
 
    }
 
}
