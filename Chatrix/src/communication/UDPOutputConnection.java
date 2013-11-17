package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPOutputConnection implements OutputConnection {
	InetAddress ip;
	DatagramSocket clientSocket;
	int clientPort;
	
	public UDPOutputConnection(DatagramSocket clientSocket, InetAddress ip, int clientPort) throws IOException {
		this.clientSocket = clientSocket;
		this.ip = ip;
		this.clientPort = clientPort;
	}
	
	public UDPOutputConnection(DatagramSocket clientSocket, InetAddress ip) throws IOException {
		 this.clientSocket = clientSocket;
		 this.ip = ip;
		 this.clientPort = clientSocket.getPort();
	}

	@Override
	public void send(String content) throws IOException {
	    content += "\n";
	    byte[] data = new byte[1024];
	    data = content.getBytes();
	    DatagramPacket packet = new DatagramPacket(data, data.length, ip, clientPort); //clientSocket.getPort()
	    clientSocket.send(packet);
	}

	@Override
	public void closeConnection() {
		clientSocket.close();
	}

}
