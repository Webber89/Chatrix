package communication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPOutputConnection implements OutputConnection {
	private OutputStream output;
	private BufferedWriter writer;
	
	public TCPOutputConnection(Socket clientSocket) throws UnknownHostException, IOException {
		output = clientSocket.getOutputStream();
		writer = new BufferedWriter(new OutputStreamWriter(output));
	}

	@Override
	public void send(String content) throws IOException {
		// TODO Håndtering af exceptions
	    System.out.println("writing to client");
		writer.write(content);
		writer.flush();
	}

	@Override
	public void ping() {
		// TODO ping
	}

}
