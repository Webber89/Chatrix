package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPInputConnection implements InputConnection {
	private BufferedReader reader;
	
	public TCPInputConnection(Socket inputSocket) throws IOException {
	    System.out.println("Reader will be created");
	    reader = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
		System.out.println("Reader created");
	}
	
	@Override
	public void listen() throws IOException {
		while (true) {
			String inputString = reader.readLine();
			if(inputString != null){
			    System.out.println(inputString);			    
			}
			try
			{
			    Thread.sleep(9999L);
			} catch (InterruptedException e)
			{
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		try {
			listen();
			System.out.println("I was listening");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
