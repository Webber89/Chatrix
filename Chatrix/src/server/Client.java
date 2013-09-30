package server;

import java.io.IOException;
import java.net.Socket;

import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;

public class Client {
	private Socket socket;
	private InputConnection input;
	public OutputConnection output;
	
	public Client(Socket socket) {
		this.socket = socket;
		try {
			input = new TCPInputConnection(socket);
			output = new TCPOutputConnection(socket);
			input.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
