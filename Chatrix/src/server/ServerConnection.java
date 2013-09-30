
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable {
	private int port;
	public String ip;
	boolean running = true;

	public ServerConnection(String ip, int port, boolean isPublic) {
		this.ip = ip;
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
			Socket socket = serverSocket.accept();
			new Client(socket).run();
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
