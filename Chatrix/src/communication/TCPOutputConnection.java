package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPOutputConnection implements OutputConnection {
	private OutputStream output;
	
	public TCPOutputConnection(Socket clientSocket) throws UnknownHostException, IOException {
		output = clientSocket.getOutputStream();
	}

	@Override
	public void send(String content) throws IOException {
		// TODO Afsendelse af beskeder 
		output.write(null);
	}

	@Override
	public void ping() {
		// TODO ping
	}

}
