package com.mpc.connection.tcpip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Client2 implements Runnable{
	
	private DataInputStream in;
	private DataOutputStream out;
	private Socket socket;
	private boolean online = false;
	public String host = null;
	public int port = 0;
	public long timeout = 10000;
	
	private List<ReceiverListener> listeners = new ArrayList<ReceiverListener>();
	private String receive;
	
	public Client2(String host,int port){
		this.host = host;
		this.port = port;
	}

	public boolean isOnline() {
		return online;
	}

	public void send(String send) throws IOException{

		int len = send.length();
		
		if (len > 0xFFFF)
			throw new IOException (len + " exceeds maximum length");
//		byte[] b = new byte[4];
//		b[0] =(byte)( len >> 24 );
//		b[1] =(byte)( (len << 8) >> 24 );
//		b[2] =(byte)( (len << 16) >> 24 );
//		b[3] =(byte)( (len << 24) >> 24 );
		
		byte[] b = new byte[2];
		b[0] =(byte)( len >> 8 );
		b[1] =(byte)( len );
		
		out.write (b);

		out.write(send.getBytes());
		out.flush();

	}
	

	private void receive() throws IOException{

		//receive
		if(in!=null){
			byte[] message= null;
			byte[] b = new byte[2];

			try{
				

				in.readFully(b,0,2);
				
//				int len = (b[3] << 12) + (b[2] << 8) + (b[1] << 4) + b[0];
				
				int len =  checkSigned(( ( b[0]) << 8), 65536) + checkSigned(b[1], 256);
				
				System.out.println("b1: "+b[1]);
				System.out.println("b0: "+b[0]);
				System.out.println("len: "+len);
				
				message= new byte[len];
				in.readFully(message);

				String x = new String(message);
				addReceive(x);
							
				
			} catch (IOException exx) {
				exx.printStackTrace();
			}

			
		}
	}
	
	private int checkSigned(int x, int shift){
		if(x < 0){
			return shift + x;
		}
		return x;
	}
	
	private void repairConnection() throws UnknownHostException, IOException{

		if(socket == null){
//			for the first time, checking the server
			try{
				socket = new Socket(host,port);
				online = true;

				out= new DataOutputStream(socket.getOutputStream());
				in= new DataInputStream(socket.getInputStream());


			} catch (IOException exx) {
				online = false;
				System.err.println("no server "+host+":"+port+" found");
				exx.printStackTrace();
			}
		}else{

			//repairing connection
			if(socket.isClosed()){
				try{
					socket = null;
					socket = new Socket(host,port);
					online = true;
				} catch (IOException exx) {
					System.err.println("server still down");
					online = false;
				}

			}
			if(online){
				out= new DataOutputStream(socket.getOutputStream());
				in= new DataInputStream(socket.getInputStream());
			}
		}
	}

	public void closeConnection() throws IOException{
		if(in!=null){
			in.close();
		}
		if(out!=null){
			out.close();
		}
		if(socket!=null)
			socket.close();
	}
	
	public void run() {
		// receive iso from vlink/ist
		while (true){
			try {
				repairConnection();
				if(online){
					receive();
				}

				Thread.sleep(500);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Listener method
	 */
	public synchronized void addReceive(String s){
		receive = s;
		fireReceiverEvent();
	}
	public synchronized void addReceiverListener( ReceiverListener l ) {
		listeners.add( l );
	}

	public synchronized void removeReceiverListener( ReceiverListener l ) {
		listeners.remove( l );
	}
	private synchronized void fireReceiverEvent() {
		ReceiverEvent receiver = new ReceiverEvent( this, receive );
		Iterator<ReceiverListener> listeners = this.listeners.iterator();
		while( listeners.hasNext() ) {
			( (ReceiverListener) listeners.next() ).receive( receiver );
		}
	}
}
