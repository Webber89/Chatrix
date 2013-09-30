package server;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import core.IllegalMessageException;

public class Message {
	private LinkedHashMap<String, String> keyValuePairs = new LinkedHashMap<String, String>();
	private Type type;

	public enum Type {
		MESSAGE("MSG", 2), JOIN("JOIN", 1), ENTER("ENT", 1), QUIT("QUIT", 0), CREATE("CRT", 2), INFO(
				"INFO", 0), INVITE("INV", 2);

		String type;
		int minParams;

		Type(String type, int minParams) {
			this.type = type;
			this.minParams = minParams;
		}
	}

	public void addKeyValue(String key, String value) {
		keyValuePairs.put(key, value);
	}

	public Message(Type type) {
		this.type = type;
		keyValuePairs.put("type", this.type.type);
	}

	// public Message(String json) {
	//
	// }

	// To JSON and back again
	public String toJson() throws JsonParseException, JsonMappingException,
			IOException, IllegalMessageException {
		if (keyValuePairs.size() <= type.minParams)
			throw new IllegalMessageException("Insufficient parameters");

		// Converts a linked hashmap of strings called keyValueParis to JSON
		// using the Gson library
		String json = new Gson().toJson(keyValuePairs);
		// Converts a linked hashmap of strings called keyValueParis to JSON
		// using an ObjectMapper from the Jackson library
		String mappedJson = new ObjectMapper()
				.writeValueAsString(keyValuePairs);

		System.out.println(json);
		System.out.println(mappedJson);

		// Converting from JSON to linked Hashmap using Gson
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> test2 = new Gson().fromJson(json,
				LinkedHashMap.class);
		for (String s : test2.keySet())
			System.out.println("key: " + s + ", value: " + test2.get(s));

		// Converting from JSON to linked hashmap using Jackson's ObjectMapper
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> test = new ObjectMapper().readValue(
				mappedJson, LinkedHashMap.class);
		for (String s : test.keySet())
			System.out.println("key: " + s + ", value: " + test.get(s));

		return json;
	}
}
