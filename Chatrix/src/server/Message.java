package server;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Message {
	private LinkedHashMap<String, String> keyValuePairs = new LinkedHashMap<String, String>();
	private Type type;
	
	public enum Type {
		MESSAGE("MSG"),
		JOIN("JOIN"),
		QUIT("QUIT"),
		CREATE("CRT"),
		INFO("INFO"),
		INVITE("INV");
		
		String type;
		
		Type(String type) {
			this.type = type;
		}
	}
	
	public void addKeyValue(String key, String value) {
		keyValuePairs.put(key, value);
	}
	
	public Message(Type type) {
		this.type = type;
		keyValuePairs.put("type", this.type.type);
	}
	
//	public Message(String json) {
//		
//	}
	
	// To JSON and back again
	public String toJson() throws JsonParseException, JsonMappingException, IOException {
		String json = new Gson().toJson(keyValuePairs);
		String mappedJson = new ObjectMapper().writeValueAsString(keyValuePairs);
		
		
		System.out.println(json);
		System.out.println(mappedJson);
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> test2 = new Gson().fromJson(json, LinkedHashMap.class);
		for (String s : test2.keySet())
			System.out.println("key: " + s + ", value: " + test2.get(s));
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> test = new ObjectMapper().readValue(mappedJson, LinkedHashMap.class);
		for (String s : test.keySet())
			System.out.println("key: " + s + ", value: " + test.get(s));
		
		return json;
	}
}
