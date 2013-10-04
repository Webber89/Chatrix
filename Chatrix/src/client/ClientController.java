package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.util.StringTokenizer;

public class ClientController {
	private String ip;
	private int port;
	private ClientConnection connection;
	private String conType;
	private String user;

	public ClientController() {
		launchClient();
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

	public void login(String user, String password) throws ConnectException,
			IllegalArgumentException {
		this.user = user;
		connection.login(user, password);
		// TODO handle login
	}
}
