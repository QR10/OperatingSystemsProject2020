package ie.gmit;

import java.io.IOException;
import java.net.*;

public class Server {
	
	private static ServerSocket listener;

	/**
	 * Main method, creates new socket and a thread for
	 * that socket
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		
		int socketId=0;
		
			
		listener = new ServerSocket(10000,10);
			 			 
			 while(true)
			 {

				System.out.println("Listening for incoming new connections");
				Socket newconnection = listener.accept();
				
				System.out.println("New connection received and spanning a thread");
				UserMarketThread t = new UserMarketThread(newconnection, socketId);
				socketId++;
				t.start();
			 }
	}// Main

}// Server