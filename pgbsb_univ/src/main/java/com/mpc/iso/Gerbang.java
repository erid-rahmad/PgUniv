package com.mpc.iso;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Properties;

import org.jpos.core.ConfigurationException;
import org.jpos.core.SimpleConfiguration;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMUX;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.util.Log4JListener;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;

public class Gerbang {
	
	private boolean signedOn;
	public static int SUM_SIGNON = 3;
	
	
	public static void main(String[] args){
		ISO87APackager packager = new ISO87APackager();
		try {
//			ASCIIChannel channel = new ASCIIChannel(packager,new ServerSocket(9999));
			ISOChannel channel = new CustomHexChannel(packager,null, new ServerSocket(9999));
			
//			channel.setTimeout(60000);
			channel.setName("testChannel");
			
//			ISOUtil.zeropad (Integer.toString (len % 0xFFFF,16), 4).getBytes();
			
			//ISOMUX mux = new ISOMUX(channel);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			System.out.println(ISOUtil.zeropad (Integer.toString (397 % 0xFFFF,16), 4));
		} catch (ISOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Gerbang(ISOMUX mux, GerbangListener listener, boolean signon, boolean custom){
		
		if(custom){
			
			ISOChannel channel = mux.getISOChannel();

			//set new packager
			InputStream isp = Gerbang.class.getClassLoader()
					.getResourceAsStream("config/iso/iso87ascii.xml");
			GenericPackager packager = null;
			try {
				packager = new GenericPackager(isp);
			} catch (ISOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			channel.setPackager(packager);
			
		}
		
		new Gerbang(mux, listener, signon);
	}
	
	public Gerbang(ISOMUX mux, GerbangListener listener, boolean signon){
		
		if(signon){
			new Thread(new SignonManager(mux, this)).start();
		}
		
		new Gerbang(mux, listener);
	}
	
	public Gerbang(ISOMUX mux, GerbangListener listener){
		
		//Run mux thread and add listener
		new Thread(mux).start();
		mux.setISORequestListener(listener);
			

		//Setup log
		InputStream isp2 = Gerbang.class.getClassLoader().getResourceAsStream("log4j.properties");
		Properties prop = new Properties();
		try {
			prop.load(isp2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Logger logger = new Logger();
		Log4JListener log4jlistener = new Log4JListener();
		try {
			log4jlistener.setConfiguration(new SimpleConfiguration(prop));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		log4jlistener.setLevel("INFO");
		logger.addListener(log4jlistener);
		ISOChannel channel = mux.getISOChannel();
		((LogSource) channel).setLogger(logger, channel.getName());
		mux.setLogger(logger, "mux-"+channel.getName());
		
		//assign to listener
		listener.setGerbang(this);
	}

	public boolean isSignedOn() {
		return signedOn;
	}

	public void setSignedOn(boolean signedOn) {
		this.signedOn = signedOn;
	}
	
	
}
