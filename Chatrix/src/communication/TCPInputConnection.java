package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import core.MotherConnection;

public class TCPInputConnection implements InputConnection {
	private BufferedReader reader;
	private MotherConnection connection;
	
	public TCPInputConnection(MotherConnection connection) throws IOException {
		this.connection = connection;
	    reader = new BufferedReader(new InputStreamReader(connection.getSocket().getInputStream()));
	}
	
	@Override
	public void listen() throws IOException {
		while (true) {
			String inputString = reader.readLine();
			if (inputString.equals("ping"));
			else if(inputString != null){
				connection.inputReceived(inputString);
			}
		}
	}

	@Override
	public void run() {
		try {
			listen();
		} catch (IOException e) {
			System.out.println("TCPInputConnection interrupted");
			connection.lostConnection();
		}
	}

}
