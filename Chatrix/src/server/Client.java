package server;

import java.io.IOException;
import java.net.Socket;

import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;

public class Client {
	public Socket socket;
	private InputConnection input;
	public OutputConnection output;
	
	public Client(Socket socket) {
		this.socket = socket;
		try {
			input = new TCPInputConnection(socket);
			output = new TCPOutputConnection(socket);
			new Thread(input).start();
			System.out.println("input started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
