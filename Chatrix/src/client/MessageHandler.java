package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.Message;

public class MessageHandler {

	private static MessageHandler messageHandler = new MessageHandler();
	private static String hostIP = "188.182.254.236";
	private int port = 7000;

	public static MessageHandler getInstance() {
		return messageHandler;
	}

	public void receiveMessage(Message message) {
		// TODO afkodning af message

	}

	public void sendMessage(String message) {

		Socket clientSocket;
		try {
			clientSocket = new Socket(hostIP, port);
			System.out.println("my IP: "
					+ clientSocket.getInetAddress().getHostAddress());
			
			OutputStream outputStream = clientSocket.getOutputStream();
			OutputStreamWriter outputWriter = new OutputStreamWriter(
					outputStream);
			BufferedWriter bWriter = new BufferedWriter(outputWriter);
			
			System.out.println("Made it");
			bWriter.write("Test Message\n");
			bWriter.close();
			clientSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
