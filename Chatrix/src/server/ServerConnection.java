
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable {
	private int port;
	boolean running = true;

	public ServerConnection(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		openClientListener();
	}

	protected void openClientListener() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
		while (running) {
		    System.out.println("Waiting for new clients");
			Socket socket = serverSocket.accept();
			Client testClient = new Client(socket);
			testClient.output.send("Test \n");
		}
		serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public void testMessage() {
		Message message = new Message(Message.Type.JOIN);
		try {
			System.out.println(message.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
