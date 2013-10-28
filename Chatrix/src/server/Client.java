package server;

import java.io.IOException;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;

import core.IllegalMessageException;
import core.Message;
import core.MotherConnection;

public class Client implements MotherConnection {
	public Socket socket;
	public InputConnection input;
	public OutputConnection output;
	private Message message;
	private String token;
	private String name;
	private boolean active = true;
	private Long lastPing;

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
		System.out.println("Message received: " + input);
		try {
			message = Message.parseJSONtoMessage(input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (message.type.type) {
		case "CMSG":
			ServerController.receivedMessage(message);
		    break;
		case "CJOIN":
			setName(message.getValue("user"));
			
			// TODO flyt logik over til ServerController
			Message returnMessage = ServerController.login(message, this);
			try {
				if (Boolean.parseBoolean(returnMessage.getValue("success"))) {
					setToken(returnMessage.getValue("token"));
					output.send(returnMessage.toJson());
					ServerController.joinRoom("public", this);
				} else {
					output.send(returnMessage.toJson());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "REG":
			setName(message.getValue("user"));
			
			Message returnMessage2 = ServerController.register(message, this);
			try {
				if (Boolean.parseBoolean(returnMessage2.getValue("success"))) {
					setToken(returnMessage2.getValue("token"));
					output.send(returnMessage2.toJson());
					ServerController.joinRoom("public", this);
				} else {
					output.send(returnMessage2.toJson());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "RJOIN":
			ServerController.activateUser(message.getValue("token"), this);
			break;
		case "ENT":
			// enter(message);
			break;
		case "LEA":
			// quit(message);
			break;
		case "CCRT":
			// create(message);
			break;
		case "CINFO":
			// info(message);
			break;
		case "CINV":
			// invite(message);
			break;
		}
	}


	public void refreshUserList(String roomName, String[] strings) {
		Message message = new Message(Message.Type.ROOM_INFO);
		message.addKeyValue("roomName", roomName);
		message.addKeyValue("users", Message.writeValueAsString(strings));
		
		try {
			output.send(message.toJson());
		} catch (JsonParseException | JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalMessageException e) {
			e.printStackTrace();
		}
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void lostConnection() {
		active = false;
		ServerController.removeClient(this);
	}

	@Override
	public void gotPing()
	{
		System.out.println("Got ping");
		lastPing = System.currentTimeMillis();
	}
	
	public Long getLastPing() {
		if (lastPing == null)
			lastPing = System.currentTimeMillis();
		return lastPing;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void reConnected(InputConnection input, OutputConnection output) {
		this.input.setMother(null);
		this.output = output;
		this.input = input;
		input.setMother(this);
		new Thread(input).start();
	}
}
