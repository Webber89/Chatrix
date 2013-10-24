package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection implements Runnable {
	private int port;
	public boolean running = true;
	public Socket socket;
	public ServerSocket serverSocket;
	private ArrayList<Client> clientList = new ArrayList<Client>();

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
				socket = serverSocket.accept();
				clientList.add(new Client(socket));
//				testClient.output.send("Test \n");
			}
		} catch (IOException e) {
			System.out.println("ServerConnection interrupted");
			try {
				serverSocket.close();
			} catch (NullPointerException | IOException ex) {
			}
		}
	}

	public void shutdown() throws IOException {
		for (Client c : clientList)
			c.output.closeConnection();
		serverSocket.close();
	}
	
	public ArrayList<Client> getClientList() {
		return clientList;
	}

}
