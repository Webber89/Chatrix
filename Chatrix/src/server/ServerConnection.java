
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
		URL cancelLink = new URL("http://thebecw.appspot.com/updatelink?sae=chat&cancel=true");
		BufferedReader in1 = new BufferedReader(new InputStreamReader(
                cancelLink.openStream()));
		String cancelMsg = in1.readLine();
		if (cancelMsg.equals("ok")) {
		} else {
			throw new Exception("Unable to cancel broadcast link");
		}
		
		URL updateLink = new URL("http://thebecw.appspot.com/updatelink?sae=chat&link=" + ip + ":" + port);
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
                updateLink.openStream()));
		String linkMsg = in2.readLine();
		if (linkMsg.equals("ok")) {
		} else {
			throw new Exception("Unable to broadcast link");
		}
		
		getIP();
	}
	
	protected void getIP() throws Exception {
		URL getLink = new URL("http://thebecw.appspot.com/spreadsheet?chat=true");
		BufferedReader in3 = new BufferedReader(new InputStreamReader(getLink.openStream()));
		String serverAddress = in3.readLine();
		StringTokenizer tokenizer = new StringTokenizer(serverAddress, ":");
		ip = tokenizer.nextToken();
		port = Integer.parseInt(tokenizer.nextToken());
		System.out.println(ip + ", " + port);
	}
}
