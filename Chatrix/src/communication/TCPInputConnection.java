package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPInputConnection implements InputConnection {
	private InputStream input;
	private InputStreamReader inputReader;
	private BufferedReader reader;
	
	public TCPInputConnection(Socket inputSocket) throws UnknownHostException, IOException {
		input = inputSocket.getInputStream();
		inputReader = new InputStreamReader(input);
		reader = new BufferedReader(inputReader);
	}
	
	@Override
	public void listen() throws IOException {
		while (true) {
			String inputString = reader.readLine();
			System.out.println(inputString);
		}
	}

}
