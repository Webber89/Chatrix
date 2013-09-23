package server;

import java.util.HashMap;

import com.google.gson.Gson;

public class Message {
	private HashMap<String, String> keyValuePairs = new HashMap<String, String>();
	
	public void addKeyValue(String key, String value) {
		keyValuePairs.put(key, value);
	}
	
	public Message(String messageType) {
		keyValuePairs.put("Type", messageType);
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(keyValuePairs);
	}
}
