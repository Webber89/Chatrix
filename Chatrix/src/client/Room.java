package client;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Room {
	private String roomName;
	private ArrayList<String> userList = new ArrayList<String>();

	public Room() {
	}

	public Room(String roomName) {
		this.roomName = roomName;

	}

	public void updateUserList(ArrayList<String> users) {

		this.userList = users;
		// userList.put(msg.getValue("users"));
		// TODO update userlist of the room.

	}
}
