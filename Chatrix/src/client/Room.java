
package client;

import java.util.ArrayList;
import java.util.List;

import core.InsertionSort;

public class Room
{
    public String roomName;
    private List<String> userList = new ArrayList<String>();

    public Room(String roomName)
    {
	this.roomName = roomName;
    }

    public void updateUserList(List<String> users){
	
	this.userList = InsertionSort.sortMoreUsers(users);
	ClientGUI.getInstance().updateUsers(userList);
    }

}


