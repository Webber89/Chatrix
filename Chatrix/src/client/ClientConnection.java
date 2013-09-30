package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import communication.TCPInputConnection;

public class ClientConnection {
	private Socket socket;
	private BufferedWriter writer;

	public ClientConnection(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			new Thread(new TCPInputConnection(socket)).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String content) throws IOException {
		writer.write(content);
		writer.flush();
	}
	
}
