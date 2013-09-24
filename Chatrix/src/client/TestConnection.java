package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class TestConnection {

	public static void main(String[] args) throws IOException {
		new TestConnection();
	}

	public TestConnection() throws IOException {
		System.out.println(getIP());
		MessageHandler.getInstance().sendMessage("asdsad");
		
		
	}

	private String getIP() throws IOException {
		URL amazonIP = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                amazonIP.openStream()));

		String ip = in.readLine();
		return ip;
	}
}
