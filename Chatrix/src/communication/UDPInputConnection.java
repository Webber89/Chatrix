package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import core.MotherConnection;

public class UDPInputConnection implements InputConnection {
	private MotherConnection connection;
	private volatile boolean isActive = true;
	private DatagramSocket inputSocket;
	
	public UDPInputConnection(MotherConnection connection, DatagramSocket inputSocket) throws SocketException {
		this.inputSocket = inputSocket;
		this.connection = connection;
	}

	@Override
	public void run() {
		try {
			listen();
		} catch (IOException e) {
			if (connection != null) {
				System.out.println("UDPInputConnection interrupted");
				connection.lostConnection();
			}
		}
	}

	@Override
	public void listen() throws IOException {
		while (isActive) {
			if (connection == null) {
				isActive = false;
			} else {
				byte[] data = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(data, data.length);
				System.out.println("Waiting for data");
				System.out.println(inputSocket.getPort());
				System.out.println(inputSocket.getLocalPort());
				System.out.println(inputSocket.getRemoteSocketAddress());
				
				inputSocket.receive(receivePacket);
				System.out.println("UDP received data");
				String inputString = new String(receivePacket.getData());
				System.out.println("Received input - inputstring:"+inputString);
				if (inputString != null) {
					if (inputString.equals("ping")) {
						connection.gotPing();
					} else {
						connection.inputReceived(inputString);
					}
				}
			}
		}
	}

	@Override
	public void setInactive() {
		isActive = false;
	}

	@Override
	public void setMother(MotherConnection connection) {
		this.connection = connection;
	}

}
