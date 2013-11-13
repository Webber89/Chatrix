package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPOutputConnection implements OutputConnection {
	InetAddress ip;
	DatagramSocket clientSocket;
	
	public UDPOutputConnection(DatagramSocket clientSocket, InetAddress ip) throws IOException {
		this.clientSocket = clientSocket;
		this.ip = ip;
	}

	@Override
	public void send(String content) throws IOException {
	    System.out.println("Writing: " + content);
	    byte[] data = content.getBytes();
	    DatagramPacket packet = new DatagramPacket(data, data.length, ip, clientSocket.getPort());
	    clientSocket.send(packet);
	}

	@Override
	public void closeConnection() {
		clientSocket.close();
	}

}
