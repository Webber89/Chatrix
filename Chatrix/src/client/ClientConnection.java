package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;
import core.IllegalMessageException;
import core.Message;

public class ClientConnection {
	private Socket socket;
	private InputConnection input;
	private OutputConnection output;
	public static Semaphore sem = new Semaphore(1, true);

	public ClientConnection(String ip, int port, String conType) {
		try {
			socket = new Socket(ip, port);
			if (conType.equals("TCP")) {
				output = new TCPOutputConnection(socket);
				input = new TCPInputConnection(socket);
			} else {
				// TODO implementér UDP
				output = null;
				input = null;
			}
			new Thread(input).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String content, String user) {
		try {
			Message message = new Message(Message.Type.MESSAGE);
			message.addKeyValue("user", user);
			message.addKeyValue("message", content);
			output.send(message.toJson());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalMessageException e) {
			e.printStackTrace();
		}
	}

	public void login(String user, String password) {
		Message message = new Message(Message.Type.JOIN);
		message.addKeyValue("user", user);
		message.addKeyValue("pass", password);
		try {
			output.sendAndLock(message.toJson());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalMessageException e) {
			e.printStackTrace();
		}
	}

	public static void release() {
		if (sem.availablePermits()==0)
			sem.release();
	}

}
