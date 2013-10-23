
package client;

import java.util.ArrayList;
import java.util.List;

public class Room
{
    public String roomName;
    private List<String> userList = new ArrayList<String>();

    public Room(String roomName)
    {
	this.roomName = roomName;
    }

    public void updateUserList(List<String> users){
	
	this.userList = users;
	ClientGUI.getInstance().updateUsers(userList);
    }

}


