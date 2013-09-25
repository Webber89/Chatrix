package server;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Message {
	private LinkedHashMap<String, String> keyValuePairs = new LinkedHashMap<String, String>();
	
	public void addKeyValue(String key, String value) {
		keyValuePairs.put(key, value);
	}
	
	public Message(String messageType) {
		keyValuePairs.put("type", messageType);
	}
	
//	public Message(String json) {
//		
//	}
	
	// To JSON and back again
	public String toJson() throws JsonParseException, JsonMappingException, IOException {
		Gson gson = new Gson();
		String json = gson.toJson(keyValuePairs);
		System.out.println(json);
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> test = new ObjectMapper().readValue(json, LinkedHashMap.class);
		for (String s : test.keySet())
			System.out.println("key: " + s + ", value: " + test.get(s));
		
		return json;
	}
}
