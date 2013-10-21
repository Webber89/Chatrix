package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.UUID;

import core.Message;

public class ServerController {
	private int port;
	public String ip;
	private ServerConnection serverCon;
	private Thread serverThread;
	private static HashMap<String, String> users = new HashMap<String, String>();
	private static File userListFile = new File(System.getProperty("user.dir")
			+ "/userlist.dat");
	private static HashMap<String, Room> rooms = new HashMap<String, Room>();
	
	public ServerController() {
		loadUserList();
		rooms.put("public", new Room("public"));
	}

	public void createServer(String conType, boolean isPrivate, int port) {
		this.port = port;
		try {
			setIP(isPrivate);
			broadcastIP(conType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (conType) {
		case "TCP":
			serverCon = new ServerConnection(port);
			serverThread = new Thread(serverCon);
			serverThread.start();
			break;
		case "UDP":
			break;
		}
	}

	protected void broadcastIP(String conType) throws Exception {
		try {
			// Contacting server with cancel param - server removes previous
			// ip/port.
			URL cancelLink = new URL(
					"http://thebecw.appspot.com/updatelink?sae=chat&cancel=true");
			BufferedReader in1 = new BufferedReader(new InputStreamReader(
					cancelLink.openStream()));
			String cancelMsg = in1.readLine();
			// Checking if msg is OK or prompt with errormsg
			if (cancelMsg.equals("ok")) {
			} else {
				throw new Exception("Unable to cancel broadcast link");
			}
			// Contacting server with params - setting the ip/port posted on
			// server
			// to the client using the updateLink
			URL updateLink = new URL(
					"http://thebecw.appspot.com/updatelink?sae=chat&link=" + ip
							+ ":" + port + ":" + conType);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(
			// Creating stream to server
					updateLink.openStream()));
			String linkMsg = in2.readLine();
			// Checking if OK - else prompt errorMSG
			if (linkMsg.equals("ok")) {
			} else {
				throw new Exception("Unable to broadcast link");
			}
		} catch (UnknownHostException e) {
			Thread.sleep(500);
			System.out.println("Failed to access appspot, trying again ...");
			broadcastIP(conType);
		}
		getIP();
	}

	private void setIP(boolean isPrivate) throws Exception {
		if (isPrivate) {
			ip = InetAddress.getLocalHost().getHostAddress();
		} else {
			URL amazonIP = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					amazonIP.openStream()));
			ip = in.readLine();
		}
	}

	protected void getIP() throws Exception {
		// Contacting server to retrieve stored ip/port
		URL getLink = new URL(
				"http://thebecw.appspot.com/spreadsheet?chat=true");
		BufferedReader in3 = new BufferedReader(new InputStreamReader(
				getLink.openStream()));
		String serverAddress = in3.readLine();
		StringTokenizer tokenizer = new StringTokenizer(serverAddress, ":");
		// reading first token before ":" seperator = the ip
		ip = tokenizer.nextToken();
		// reading the port, which is the next token
		port = Integer.parseInt(tokenizer.nextToken());
		System.out.println(ip + ", " + port);
	}

	public void stopServer() {
		try {
			System.out.println("Stopping server");
			serverCon.shutdown();
			// serverSocket.close();
		} catch (Exception e) {
			System.out.println("Failed to stop server");
		}
	}

	public static Message sendMessage(Message message) {

		return message;

	}

	public static Message login(Message message, Client c) {
		String user = message.keyValuePairs.get("user");
		String pass = message.keyValuePairs.get("pass");
		Message returnMessage = new Message(Message.Type.SERVER_JOIN);
		if (users.containsKey(user)) {
			if (users.get(user).equals(pass)) {
				returnMessage.addKeyValue("success", "true");
				returnMessage.addKeyValue("token", generateToken());
				rooms.get("public").addClient(c);
				return returnMessage;
			}
		}
		returnMessage.addKeyValue("success", "false");
		returnMessage.addKeyValue("message", "Invalid username or password");
		return returnMessage;
	}

	private static String generateToken() {
		return UUID.randomUUID().toString();
	}

	@SuppressWarnings("unchecked")
	private static void loadUserList() {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream(userListFile));
			users = (HashMap<String, String>) in.readObject();
			for (String s : users.keySet())
				System.out.println("User: " + s);
		} catch (FileNotFoundException e) {
			System.out.println("Userlist does not exist");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private static void saveUserList() {
		ObjectOutputStream out;
		try {
			userListFile.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(userListFile));
			out.writeObject(users);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
