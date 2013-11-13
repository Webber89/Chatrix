package server;

import java.io.IOException;
import java.util.ArrayList;

public interface ServerConnection extends Runnable {

	public void openClientListener();
	
	public void shutdown() throws IOException;
	
	public ArrayList<Client> getClientList();
}
