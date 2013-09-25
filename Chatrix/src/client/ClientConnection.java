package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;

public class ClientConnection {
	private String serverIP;
	private int serverPort;

	public ClientConnection() {

	}

	protected void runClient() throws Exception {
		getIP();
		Socket clientSocket = new Socket(serverIP, serverPort);
		OutputStream outputStream = clientSocket.getOutputStream();
		OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
		BufferedWriter bWriter = new BufferedWriter(outputWriter);

		bWriter.write("Random string\n");
		bWriter.close();
		clientSocket.close();
	}

	protected void getIP() throws Exception {
		URL getLink = new URL(
				"http://thebecw.appspot.com/spreadsheet?chat=true");
		BufferedReader in3 = new BufferedReader(new InputStreamReader(
				getLink.openStream()));
		String serverAddress = in3.readLine();
		StringTokenizer tokenizer = new StringTokenizer(serverAddress, ":");
		serverIP = tokenizer.nextToken();
		serverPort = Integer.parseInt(tokenizer.nextToken());
		System.out.println(serverIP + ", " + serverPort);
	}
}
