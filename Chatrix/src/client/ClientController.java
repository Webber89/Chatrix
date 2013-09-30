package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class ClientController {
	private String ip;
	private int port;
	private ClientConnection connection;
	
	public ClientController(String conType) {
		launchClient(conType);
	}
	
	public void launchClient(String conType) {
		try {
			getIP();
			connection = new ClientConnection(ip, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String message) {
		connection.send(message);
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
		System.out.println(ip + ", " + port);
	}
}
