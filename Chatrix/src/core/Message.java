package core;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
	private LinkedHashMap<String, String> keyValuePairs = new LinkedHashMap<String, String>();
	private Type type;

	public enum Type {
		MESSAGE("MSG", 2), JOIN("JOIN", 2), ENTER("ENT", 1), QUIT("QUIT", 0), CREATE(
				"CRT", 2), INFO("INFO", 0), INVITE("INV", 2);

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

	// To JSON and back again
	public String toJson() throws JsonParseException, JsonMappingException,
			IOException, IllegalMessageException {
		if (keyValuePairs.size() <= type.minParams)
			throw new IllegalMessageException("Insufficient parameters");

		// Converts a linked hashmap of strings called keyValueParis to JSON
		// using an ObjectMapper from the Jackson library
		String mappedJson = new ObjectMapper()
				.writeValueAsString(keyValuePairs);

		// System.out.println(mappedJson);

		// Converting from JSON to linked hashmap using Jackson's ObjectMapper

		return mappedJson;
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> parseJSON(String json)
			throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, LinkedHashMap.class);

	}
}
