package server;

import java.io.IOException;
import java.net.Socket;

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
	private Message message;

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
		System.out.println(input);
		try {
			message = Message.parseJSONtoMessage(input);
		} catch (JsonParseException | JsonMappingException e) {
			// TODO Handle invalid JSON
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Handle connection failure
			e.printStackTrace();
		}

		switch (message.type.type) {

		case "MESSAGE":
			// msg(message);
			break;
		case "JOIN":
			// join(message);
			break;
		case "ENTER":
			// enter(message);
			break;
		case "QUIT":
			// quit(message);
			break;
		case "CREATE":
			// create(message);
			break;
		case "INFO":
			// info(message);
			break;
		case "Invite":
			// invite(message);
			break;
		}
	}

}
