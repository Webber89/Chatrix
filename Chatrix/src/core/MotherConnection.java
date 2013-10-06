package core;

import java.net.Socket;

public interface MotherConnection {
	public Socket getSocket();
	
	public void inputReceived(String input);
}
