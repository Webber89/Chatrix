package server;

import java.io.IOException;
import java.net.Socket;

import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;

import core.MotherConnection;

public class Client implements MotherConnection {
	public Socket socket;
	private InputConnection input;
	public OutputConnection output;
	
	public Client(Socket socket) {
		this.socket = socket;
		try {
			input = new TCPInputConnection(this);
			output = new TCPOutputConnection(socket);
			new Thread(input).start();
			System.out.println("input started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Socket getSocket() {
		return socket;
	}

	@Override
	public void inputReceived(String input) {
		System.out.println(input);
	}
	
}
