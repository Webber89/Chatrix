package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class UDPServerConnection implements ServerConnection {
	private int port;
	private DatagramSocket serverSocket;
	private volatile boolean running = true;
	private HashMap<String, Client> clientMap = new HashMap<String, Client>();

	public UDPServerConnection(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		openClientListener();
	}

	@Override
	public void openClientListener() {
		try {
			serverSocket = new DatagramSocket(port);
			while (running) {
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				serverSocket.receive(packet);
				String input = new String(data);
				InetAddress addr = packet.getAddress();
				String ip = addr.toString();
				int clientPort = packet.getPort();
				System.out.println("Client's address: " + ip + ", " + packet.getPort());
				// TODO giv klienter deres egen port
				if (!clientMap.containsKey(ip)) {
//					String hello = "Hello!";
//					DatagramPacket helloPacket = new DatagramPacket(hello.getBytes(), hello.getBytes().length, addr, packet.getPort());
//					serverSocket.send(helloPacket);
//					DatagramSocket clientSocket = new DatagramSocket();
//					clientSocket.connect(packet.getAddress(), packet.getPort());
					clientMap.put(ip, new Client(addr, clientPort, serverSocket));
				}
				clientMap.get(ip).inputReceived(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void shutdown() throws IOException {
		for (Client c : clientMap.values())
			c.output.closeConnection();
		serverSocket.close();
	}

	@Override
	public ArrayList<Client> getClientList() {
		return new ArrayList<Client>(clientMap.values());
	}

}
