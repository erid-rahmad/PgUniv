package com.mpc.iso;

import java.io.IOException;
import java.net.ServerSocket;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.HEXChannel;

public class CustomHexChannel extends HEXChannel{

	public CustomHexChannel(ISOPackager packager, byte[] TPDU,
			ServerSocket serverSocket) throws IOException {
		super(packager, TPDU, serverSocket);
	}
	
	public CustomHexChannel (String host, int port, ISOPackager p, byte[] TPDU) {
		super(host, port, p, TPDU);
	}

	protected void sendMessageLength(int len) throws IOException {
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
		
		serverOut.write (b);
	}
	
	protected int getMessageLength() throws IOException, ISOException {
		byte[] b = new byte[2];
		serverIn.readFully(b,0,2);
		
//		int len = (b[3] << 12) + (b[2] << 8) + (b[1] << 4) + b[0];
		
		int len =  checkSigned(( ( b[0]) << 8), 65536) + checkSigned(b[1], 256);
		
		System.out.println("b1: "+b[1]);
		System.out.println("b0: "+b[0]);
		System.out.println("len: "+len);
		
		return len;
	}
	
	private int checkSigned(int x, int shift){
		if(x < 0){
			return shift + x;
		}
		return x;
	}
	
//	public static void main(String[] args){
//		int len = 543 >> 8;
//		System.out.println(len);
//		
//		System.out.println(Integer.toString(0x83, 16));
//		
//		byte bx = (byte) 0x83;
//		
////		System.out.println(checkSigned(bx));
//	}
}
