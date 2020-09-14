package com.mpc.iso;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMUX;

public class SignonManager implements Runnable{
	
	private ISOMUX mux;
	private Gerbang gerbang;
	
	public SignonManager(ISOMUX mux, Gerbang gerbang) {
		this.mux = mux;
		this.gerbang = gerbang;
	}

	public void run() {

		boolean doSignon = false;
		boolean afterDisconnected = true;
		
		while(true){
			
			if(mux.isConnected() && afterDisconnected){
				doSignon = true;
			}
				
			
			if(mux.isConnected() && doSignon){
				
				try {
					
					for(int i=0;i<Gerbang.SUM_SIGNON;i++){
						
						mux.send(Generator.createSignon());
					
						//waiting sign-on for 15s
						try {
							Thread.sleep(15000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						//check sign-on
						if(gerbang.isSignedOn()){
							break;
						}
					
					}
					
					
				} catch (ISOException e) {
					e.printStackTrace();
				}
				
				doSignon = false;
				afterDisconnected = false;
				
			}
			
			if(!mux.isConnected()){
				afterDisconnected = true;
				//gerbang.setSignedOn(false);
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
