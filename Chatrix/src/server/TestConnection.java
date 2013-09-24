package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import client.MessageHandler;

public class TestConnection {

	public static void main(String[] args) throws IOException {
		new TestConnection();
	}

	public TestConnection() throws IOException {
		MessageHandler.getInstance().sendMessage("asdsad");
//		System.out.println(getIP());
//		ServerSocket serverSocket = new ServerSocket(7000);
//		Socket socket = serverSocket.accept();
//		System.out.println(socket.getInetAddress().getHostAddress());
//		
//		InputStream is = socket.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//        boolean done = false;
//      
//        String message = br.readLine();
//        String msgOut ="";
//        
//        while ((message = br.readLine()) != null) {
//            msgOut = msgOut.concat(message);  
//        }
//        System.out.println(msgOut);
//		serverSocket.close();
	}

	private String getIP() throws IOException {
		URL amazonIP = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                amazonIP.openStream()));

		String ip = in.readLine();
		return ip;
	}
}
