package com.mpc.connection.tcpip;

public class ClientHandler {

	
	public ClientHandler(Client2 client2, ClientHandlerListener clientListener){
		
		client2.addReceiverListener(clientListener);
		
		new Thread(client2).start();
	}
}
