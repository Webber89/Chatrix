package communication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPOutputConnection implements OutputConnection {
	private BufferedWriter writer;
	
	public TCPOutputConnection(Socket clientSocket) throws UnknownHostException, IOException {
		writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	}

	@Override
	public void send(String content) throws IOException {
		writer.flush();
		// TODO Håndtering af exceptions
	    System.out.println("Writing: " + content);
		writer.write(content);
//		closeConnection();
	}

	@Override
	public void ping() {
		// TODO ping
	}
	
	public void closeConnection() {
		try {
			System.out.println("Closing outputstream writer ...");
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to close writer.");
		}
	}

}
