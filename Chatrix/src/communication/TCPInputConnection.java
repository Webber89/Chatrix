package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import client.ClientConnection;

public class TCPInputConnection implements InputConnection {
	private BufferedReader reader;
	
	public TCPInputConnection(Socket inputSocket) throws IOException {
	    reader = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
	}
	
	@Override
	public void listen() throws IOException {
		while (true) {
			String inputString = reader.readLine();
			if(inputString != null){
			    ClientConnection.release();
				System.out.println(inputString);
			    
			}
			try
			{
			    Thread.sleep(99L);
			} catch (InterruptedException e)
			{
			    e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		try {
			listen();
		} catch (IOException e) {
			System.out.println("TCPInputConnection interrupted");
//			e.printStackTrace();
		}
	}

}
