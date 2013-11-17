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
				inputSocket.receive(receivePacket);
				String inputString = new String(receivePacket.getData());
				inputString = inputString.substring(0,inputString.indexOf('\n'));
				System.out.println("input:"+inputString);
				
				if (inputString != null) {
					if (inputString.startsWith("ping")) {
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
	}

	@Override
	public void setMother(MotherConnection connection) {
		this.connection = connection;
	}

}
