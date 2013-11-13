package communication;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPOutputConnection implements OutputConnection {
	
	public UDPOutputConnection(DatagramSocket clientSocket, InetAddress ip) throws IOException {
	}

	@Override
	public void send(String content) throws IOException {
	    System.out.println("Writing: " + content);
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub

	}

}
