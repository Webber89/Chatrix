package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import core.MotherConnection;

public class TCPInputConnection implements InputConnection {
	private BufferedReader reader;
	private MotherConnection connection;
	private volatile boolean isActive = true;

	public TCPInputConnection(MotherConnection connection) throws IOException {
		this.connection = connection;
		reader = new BufferedReader(new InputStreamReader(connection
				.getSocket().getInputStream()));
	}

	@Override
	public void listen() throws IOException {
		while (isActive) {
			if (connection == null) {
				isActive = false;
			} else {
				String inputString = reader.readLine();
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
	public void run() {
		try {
			listen();
		} catch (IOException e) {
			if (connection != null) {
				System.out.println("TCPInputConnection interrupted");
				connection.lostConnection();
			}
		}
	}

	public void setInactive() {
		isActive = false;
	}

	public void setMother(MotherConnection connection) {
		this.connection = connection;
	}
}
