package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.Message;

public class MessageHandler {

	private static MessageHandler messageHandler = new MessageHandler();
	private static String host = "1.1.1.0";
	private int port = 9999;

	public static MessageHandler getInstance() {
		return messageHandler;
	}

	public void receiveMessage(Message message) {
		// TODO afkodning af message

	}

	public void sendMessage(Message message) {

		Socket clientSocket;
		try {
			clientSocket = new Socket(host, port);
			System.out.println("Socket with IP: "
					+ clientSocket.getInetAddress().getHostAddress());
			OutputStream outputStream = clientSocket.getOutputStream();
			OutputStreamWriter outputWriter = new OutputStreamWriter(
					outputStream);
			BufferedWriter bWriter = new BufferedWriter(outputWriter);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
