package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

import client.MessageHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import com.google.gson.stream.JsonReader;

public class TestConnection {

	public static void main(String[] args) throws IOException {
		Message message = new Message("MSG");
		message.addKeyValue("room", "public");
		message.addKeyValue("content", "hello, world");
		message.toJson();
//		new TestConnection();
	}

	public TestConnection() throws IOException {
		MessageHandler.getInstance().sendMessage("asdsad");
//		System.out.println(getIP());
		ServerSocket serverSocket = new ServerSocket(7000);
		Socket socket = serverSocket.accept();
//		System.out.println(socket.getInetAddress().getHostAddress());
//		
		InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        
        JsonStreamParser parser = new JsonStreamParser(isr);
        JsonElement element = parser.next();
        JsonObject jObject = element.getAsJsonObject();
        jObject.get("type");
        
        JsonReader reader = new JsonReader(isr);
        reader.beginArray();
        HashMap<String, String> keyValuePairs = new HashMap<String, String>();
        while (reader.hasNext()) {
        	reader.beginObject();
        	String key = reader.nextName();
        	String value = reader.nextString();
        	keyValuePairs.put(key, value);
        	reader.endObject();
        }
        
        reader.close();
//        BufferedReader br = new BufferedReader(isr);
//        boolean done = false;
//      
//        String message = br.readLine();
//        String msgOut ="";
//        
//        while ((message = br.readLine()) != null) {
//            msgOut = msgOut.concat(message);  
//        }
//        System.out.println(msgOut);
		serverSocket.close();
	}

	protected String getIP() throws IOException {
		URL amazonIP = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                amazonIP.openStream()));

		String ip = in.readLine();
		return ip;
	}
}
