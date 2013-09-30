
package server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;

public class ServerConnection {
	private int port;
	public String ip;

	public ServerConnection(int port, boolean isPublic) throws Exception {
		this.port = port;
		setIP(isPublic);
		broadcastIP();
		runServer();
//		runClient();
	}

	private void setIP(boolean isPublic) throws Exception {
		if (isPublic) {
		URL amazonIP = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                amazonIP.openStream()));
		ip = in.readLine();
		} else {
		ip = InetAddress.getLocalHost().getHostAddress();
		}
	}

	protected void runServer() throws Exception {
		ServerSocket serverSocket = new ServerSocket(port);
		Socket socket = serverSocket.accept();

		InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        
        BufferedReader br = new BufferedReader(isr);
        String message = br.readLine();
        System.out.println(message);
        
        String msgOut ="";
        while ((message = br.readLine()) != null) {
            msgOut = msgOut.concat(message);  
        }

		serverSocket.close();
	}

	protected void broadcastIP() throws Exception {
//	    	Contacting server with cancel param - server removes previous ip/port.
		URL cancelLink = new URL("http://thebecw.appspot.com/updatelink?sae=chat&cancel=true");
		BufferedReader in1 = new BufferedReader(new InputStreamReader(
//              Creating stream to server
		cancelLink.openStream()));
		String cancelMsg = in1.readLine();
//		Checking if msg is OK or prompt with errormsg
		if (cancelMsg.equals("ok")) {
		} else {
			throw new Exception("Unable to cancel broadcast link");
		}
//		Contacting server with params - setting the ip/port posted on server to the client using the updateLink
		URL updateLink = new URL("http://thebecw.appspot.com/updatelink?sae=chat&link=" + ip + ":" + port);
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
//              Creating stream to server
		updateLink.openStream()));
		String linkMsg = in2.readLine();
//		Checking if OK - else prompt errorMSG 
		if (linkMsg.equals("ok")) {
		} else {
			throw new Exception("Unable to broadcast link");
		}
		getIP();
	}
	
	protected void getIP() throws Exception {
//	    	Contacting server to retrieve stored ip/port
		URL getLink = new URL("http://thebecw.appspot.com/spreadsheet?chat=true");
		BufferedReader in3 = new BufferedReader(new InputStreamReader(getLink.openStream()));
		String serverAddress = in3.readLine();
		StringTokenizer tokenizer = new StringTokenizer(serverAddress, ":");
//		reading first token before ":" seperator = the ip 
		ip = tokenizer.nextToken();
//		reading the port, which is the next token
		port = Integer.parseInt(tokenizer.nextToken());
		System.out.println(ip + ", " + port);
	}
	
	public void testMessage() {
		Message message = new Message(Message.Type.JOIN);
		try {
			System.out.println(message.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
