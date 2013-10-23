package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.StringTokenizer;

import core.Message;

public class ClientController {
	private static ClientController instance = new ClientController();
	private String ip;
	private int port;
	private ClientConnection connection;
	private String conType;
	private String user;
	private String token;
	private HashMap<String,Room> rooms = new HashMap<String,Room>();

	private ClientController() {
		launchClient();
	}
	
	public static ClientController getInstance() {
		return instance;
	}
	public void launchClient() {
		try {
			getIP();
			connection = new ClientConnection(ip, port, conType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String message) {
		connection.sendMessage(message, user);
	}

	protected void getIP() throws Exception {
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

	public void login(String user, String password) {
		this.user = user;
		try {
		connection.login(user, password);
		} catch (Exception e) {
			ClientLogin.showErrorMessage(e.getMessage());
		}
	}
	
	public void handleLogin(Message message) {
		if (Boolean.parseBoolean(message.getValue("success"))) {
			token = message.getValue("token");
			System.out.println(token);
			ClientLogin.startClient();
		} else {
			ClientLogin.showErrorMessage(message.getValue("message"));
		}
		
	}

	public void handleJoinRoom(Message message)
	{
	    Room temp;
	    if(!rooms.containsKey(message.getValue("roomName"))){
		System.out.println(message.getValue("roomName"));
		rooms.put(message.getValue("roomName"), temp = new Room(message.getValue("roomName")));
	    }
	    else{
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
}
