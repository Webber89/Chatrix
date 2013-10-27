package server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import communication.BatchSender;

import core.Message;

public class Room {
	HashMap<String, Client> userList = new HashMap<String, Client>();
	public String roomName;
	private BatchSender sender = BatchSender.getInstance();

	public Room(String roomName) {
		this.roomName = roomName;
	}

	private void refreshUserList() {
		ArrayList<String> userNames = new ArrayList<String>();
		for (Client c : userList.values()) {
			userNames.add(c.getName());
		}
		Message message = new Message(Message.Type.ROOM_INFO);
		message.addKeyValue("roomName", roomName);
		message.addKeyValue("users", Message.writeValueAsString(userNames
				.toArray(new String[userNames.size()])));

		for (Client c : userList.values())
			sender.submit(c.output, message);
	}

	public void addClient(Client c) {
		userList.put(c.getToken(), c);
		refreshUserList();
	}

	public void removeClient(Client client) {
		if (userList.containsKey(client.getToken())) {
			userList.remove(client.getToken());
			refreshUserList();
		}
	}

	public void broadcastMessage(Message message) {
		Message returnMessage = new Message(Message.Type.SERVER_MESSAGE);
		returnMessage.addKeyValue("roomName", roomName);
		returnMessage.addKeyValue("user",userList.get(message.getValue("token")).getName());
		returnMessage.addKeyValue("message", message.getValue("message"));
		returnMessage.addKeyValue("timestamp", getTime());
		for (Client c : userList.values()) {
			if (c.isActive()) {
			sender.submit(c.output, returnMessage);
			} else {
				// TODO add to messagebuffer
			}
		}
	}

	private String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		return format.format(Calendar.getInstance().getTime());
	}

}
