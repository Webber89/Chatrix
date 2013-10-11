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
import core.MotherConnection;

public class ClientConnection implements MotherConnection {
	private Socket socket;
	private InputConnection input;
	private OutputConnection output;
	public static Semaphore sem = new Semaphore(0, true);

	public ClientConnection(String ip, int port, String conType) {
		try {
			socket = new Socket(ip, port);
			if (conType.equals("TCP")) {
				output = new TCPOutputConnection(socket);
				input = new TCPInputConnection(this);
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
			Message message = new Message(Message.Type.CLIENT_MESSAGE);
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
		Message message = new Message(Message.Type.CLIENT_JOIN);
		message.addKeyValue("user", user);
		message.addKeyValue("pass", password);
		try {
			output.send(message.toJson());
			sem.acquire();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			sem.release();
			e.printStackTrace();
		} catch (IllegalMessageException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void release() {
		if (sem.availablePermits()==0)
			sem.release();
	}

	@Override
	public Socket getSocket() {
		return socket;
	}

	@Override
	public void inputReceived(String input) {
		release();
		System.out.println(input);
	}

}
