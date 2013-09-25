
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;

public class TestConnection {
	private static final int PORT = 16000;
	public static String serverIP;
	public static int serverPort;

	public static void main(String[] args) throws Exception {
//		Message message = new Message("MSG");
//		message.addKeyValue("room", "public");
//		message.addKeyValue("content", "hello, world");
//		message.toJson();
		new TestConnection();
	}

	public TestConnection() throws Exception {
//		runServer();
		runClient();
	}

	protected void runClient() throws Exception {
		getIP();
		Socket clientSocket = new Socket(serverIP, serverPort);
		OutputStream outputStream = clientSocket.getOutputStream();
		OutputStreamWriter outputWriter = new OutputStreamWriter(
				outputStream);
		BufferedWriter bWriter = new BufferedWriter(outputWriter);
		
		bWriter.write("Random string\n");
		bWriter.close();
		clientSocket.close();
	}

	protected void runServer() throws Exception {
		setIP();
		ServerSocket serverSocket = new ServerSocket(PORT);
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

	protected void setIP() throws Exception {
//		URL amazonIP = new URL("http://checkip.amazonaws.com");
//		BufferedReader in = new BufferedReader(new InputStreamReader(
//		                amazonIP.openStream()));
//
//		String ip = in.readLine();
		String ip = InetAddress.getLocalHost().getHostAddress();
		URL cancelLink = new URL("http://thebecw.appspot.com/updatelink?sae=chat&cancel=true");
		BufferedReader in1 = new BufferedReader(new InputStreamReader(
                cancelLink.openStream()));
		String cancelMsg = in1.readLine();
		System.out.println(cancelMsg);
		
		URL updateLink = new URL("http://thebecw.appspot.com/updatelink?sae=chat&link=" + ip + ":" + PORT);
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
                updateLink.openStream()));
		String linkMsg = in2.readLine();
		System.out.println(linkMsg);
		
		getIP();
	}
	
	protected void getIP() throws Exception {
		URL getLink = new URL("http://thebecw.appspot.com/spreadsheet?chat=true");
		BufferedReader in3 = new BufferedReader(new InputStreamReader(getLink.openStream()));
		String serverAddress = in3.readLine();
		StringTokenizer tokenizer = new StringTokenizer(serverAddress, ":");
		serverIP = tokenizer.nextToken();
		serverPort = Integer.parseInt(tokenizer.nextToken());
		System.out.println(serverIP + ", " + serverPort);
	}
}
