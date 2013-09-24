package client;

import server.Message;


public class MessageHandler {
	private static MessageHandler messageHandler = new MessageHandler();
	
	public static MessageHandler getInstance() {
		return messageHandler;
	}
	
	public void receiveMessage(Message message) {
		// TODO afkodning af message
	}
	
}
