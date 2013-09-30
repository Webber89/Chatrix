package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPInputConnection implements InputConnection {
	private BufferedReader reader;
	
	public TCPInputConnection(Socket inputSocket) throws IOException {
		reader = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
	}
	
	@Override
	public void listen() throws IOException {
		while (true) {
			String inputString = reader.readLine();
			System.out.println(inputString);
		}
	}

	@Override
	public void run() {
		try {
			listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
