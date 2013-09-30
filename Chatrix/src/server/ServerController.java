package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.StringTokenizer;

public class ServerController {
	private int port;
	public String ip;

	public ServerController() {
	}

	public void createServer(String conType, boolean isPrivate, int port) {
		this.port = port;
		switch (conType) {
		case "TCP":
			break;
		case "UDP":
			break;
		}
		try {
			setIP(isPrivate);
			broadcastIP();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void broadcastIP() throws Exception {
		// Contacting server with cancel param - server removes previous
		// ip/port.
		URL cancelLink = new URL(
				"http://thebecw.appspot.com/updatelink?sae=chat&cancel=true");
		BufferedReader in1 = new BufferedReader(new InputStreamReader(
		// Creating stream to server
				cancelLink.openStream()));
		String cancelMsg = in1.readLine();
		// Checking if msg is OK or prompt with errormsg
		if (cancelMsg.equals("ok")) {
		} else {
			throw new Exception("Unable to cancel broadcast link");
		}
		// Contacting server with params - setting the ip/port posted on server
		// to the client using the updateLink
		URL updateLink = new URL(
				"http://thebecw.appspot.com/updatelink?sae=chat&link=" + ip
						+ ":" + port);
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
		// Creating stream to server
				updateLink.openStream()));
		String linkMsg = in2.readLine();
		// Checking if OK - else prompt errorMSG
		if (linkMsg.equals("ok")) {
		} else {
			throw new Exception("Unable to broadcast link");
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
}
