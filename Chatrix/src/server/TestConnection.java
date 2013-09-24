package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;

public class TestConnection {

	public static void main(String[] args) throws IOException {
		new TestConnection();
	}

	public TestConnection() throws IOException {
		System.out.println(getIP());
		ServerSocket socket = new ServerSocket(7000);
		socket.accept();
		System.out.println("Got connection!");
		socket.close();
	}

	private String getIP() throws IOException {
		URL amazonIP = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                amazonIP.openStream()));

		String ip = in.readLine();
		return ip;
	}
}
