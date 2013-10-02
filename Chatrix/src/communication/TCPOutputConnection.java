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
		// TODO Håndtering af exceptions
	    System.out.println("writing to client");
		writer.write(content);
		System.out.println("Wrote to client");
		writer.flush();
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
