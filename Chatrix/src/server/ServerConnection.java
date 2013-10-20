package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable {
	private int port;
	public boolean running = true;
	public Socket socket;
	public ServerSocket serverSocket;
	private Client testClient;

	public ServerConnection(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		openClientListener();
	}

	protected void openClientListener() {
		try {
			serverSocket = new ServerSocket(port);
			while (running) {
				System.out.println("Waiting for new clients");
				socket = serverSocket.accept();
				testClient = new Client(socket);
//				testClient.output.send("Test \n");
			}
		} catch (IOException e) {
			System.out.println("ServerConnection interrupted");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	public void shutdown() throws IOException {
		testClient.output.closeConnection();
		serverSocket.close();
		
	}

}
