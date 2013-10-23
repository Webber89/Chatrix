package server;

import java.util.ArrayList;
import java.util.HashMap;

public class Room
{
    HashMap<String, Client> userList = new HashMap<String, Client>();
    public String roomName;
    
    public Room(String roomName) {
    	this.roomName = roomName;
    }
    
    private void refreshUserList() {
    	System.out.println("No. of users: " + userList.size());
    	ArrayList<String> userNames = new ArrayList<String>();
    	for (Client c : userList.values()) {
    		userNames.add(c.getName());
    		System.out.println(c.getName());
    	}
    	for (Client c : userList.values())
    		c.refreshUserList(roomName, userNames.toArray(new String[userNames.size()]));
    }
    
    public void addClient(Client c) {
    	userList.put(c.getToken(), c);
    	refreshUserList();
    }

	public void removeClient(Client client) {
		if (userList.containsKey(client.getName())) {
			userList.remove(client.getName());
			refreshUserList();
		}
	}
    
}
