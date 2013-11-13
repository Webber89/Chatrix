package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import core.Message;

public class ClientController
{
    private static ClientController instance = new ClientController();
    private String ip;
    private int port;
    private ClientConnection connection;
    private String conType;
    @SuppressWarnings("unused")
    private String user;
    private String token;

    private HashMap<String, Room> rooms = new HashMap<String, Room>();
    private boolean isConnected = true;

    private ClientController()
    {
	launchClient();
    }

    public static ClientController getInstance()
    {
	return instance;
    }

    public void launchClient()
    {
	try
	{
	    getIP();
	    connection = new ClientConnection();
	    connection.connect(ip, port, conType);
	    ClientLogin.showStatusMessage("Please enter credentials");
	} catch (Exception e)
	{
	    e.printStackTrace();
	    ClientLogin.showStatusMessage("Failed to connect to server");
	}

    }

    public void send(String room, String message)
    {
	connection.sendMessage(room, message, token);
    }

    protected void getIP() throws Exception
    {
	URL getLink = new URL(
		"http://thebecw.appspot.com/spreadsheet?chat=true");
	BufferedReader in3 = new BufferedReader(new InputStreamReader(
		getLink.openStream()));
	String serverAddress = in3.readLine();
	StringTokenizer tokenizer = new StringTokenizer(serverAddress, ":");
	ip = tokenizer.nextToken();
	port = Integer.parseInt(tokenizer.nextToken());
	conType = tokenizer.nextToken();
	System.out.println(ip + ", " + port + ", " + conType);
    }

    public void login(String user, String password)
    {
	this.user = user;
	try
	{
	    connection.login(user, password);
	} catch (Exception e)
	{
	    ClientLogin.showStatusMessage(e.getMessage());
	}
    }

    public void handleLogin(Message message)
    {
	if (Boolean.parseBoolean(message.getValue("success")))
	{
	    token = message.getValue("token");
	    System.out.println(token);
	    ClientLogin.startClient();
	} else
	{
	    ClientLogin.showStatusMessage(message.getValue("message"));
	}

    }

    public void handleJoinRoom(Message message)
    {
	Room temp;
	if (!rooms.containsKey(message.getValue("roomName")))
	{
	    System.out.println(message.getValue("roomName"));
	    rooms.put(message.getValue("roomName"),
		    temp = new Room(message.getValue("roomName")));
	    ClientGUI.getInstance().updateRooms(rooms.keySet());
	} else
	{
	    temp = rooms.get(message.getValue("roomName"));
	}
	try
	{
	    temp.updateUserList(Message.toArrayList(message.getValue("users")));
	} catch (Exception e)
	{
	    e.printStackTrace();
	}

    }

    public void handleServerInfo(Message message)
    {

    }

    public void handleMessage(Message message)
    {
	if (rooms.containsKey(message.getValue("roomName")))
	{
	    // rooms.get(message.getValue("roomName")).postMessage(message);
	    ClientGUI.getInstance().addMessage(message.getValue("user"),
		    message.getValue("message"), message.getValue("timestamp"));
	}
    }

    public void register(String user, String pass)
    {
	connection.register(user, pass);
    }

    public void lostConnection()
    {
	if (!isConnected)
	{

	} else
	{
	    ClientGUI.getInstance().lostConnection();
	    isConnected = false;
	    try
	    {
		connection.reconnect();
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
    }
    public void regainedConnection(){
	ClientGUI.getInstance().reconnected();
	isConnected = true;
    }
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public void handleBufferMessages(Message message)
    {
	try
	{
	    List<String> bufferMessages = Message.toArrayList(message.getValue("messages"));
	    for (String s : bufferMessages){
		handleMessage(Message.parseJSONtoMessage(s));
	    }
	} catch (Exception e)
	{
	    e.printStackTrace();
	} 
	
    }

    public void logout()
    {
	connection.logout(token);
    }
}
