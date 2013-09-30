package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;

public class ClientConnection {
	private Socket socket;
	private InputConnection input;
	private OutputConnection output;

	public ClientConnection(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			output = new TCPOutputConnection(socket);
			input = new TCPInputConnection(socket);
			new Thread(input).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String content) {
		try {
			output.send(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
