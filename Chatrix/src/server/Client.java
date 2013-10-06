package server;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;
import core.Message;
import core.MotherConnection;

public class Client implements MotherConnection {
	public Socket socket;
	private InputConnection input;
	public OutputConnection output;
	
	public Client(Socket socket) {
		this.socket = socket;
		try {
			input = new TCPInputConnection(this);
			output = new TCPOutputConnection(socket);
			new Thread(input).start();
			System.out.println("input started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Socket getSocket() {
		return socket;
	}

	@Override
	public void inputReceived(String input) {
		try {
			LinkedHashMap<String, String> message = Message.parseJSON(input);
			for (String s : message.keySet()) {
				System.out.println(s + ": " + message.get(s));
			}
		} catch (JsonParseException | JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(input);
	}
	
}
