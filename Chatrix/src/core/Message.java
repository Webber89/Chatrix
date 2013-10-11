package core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
	public LinkedHashMap<String, String> keyValuePairs = new LinkedHashMap<String, String>();
	public Type type;
	// Map of all Types
	private static final Map<String, Type> typeMap;
	// Statically initialises map
	static {
		typeMap = new HashMap<String, Type>();
		for (Type t : Type.values())
			typeMap.put(t.type, t);
	}

	public enum Type {
		CLIENT_MESSAGE("CMSG", 3), SERVER_MESSAGE("SMSG", 4), 
		CLIENT_JOIN("CJOIN", 2), SERVER_JOIN("SJOIN", 2), 
		ENTER("ENT", 2), LEAVE("LEA", 1),
		CLIENT_CREATE("CCRT", 2), SERVER_CREATE("SCRT", 2),
		CLIENT_INFO("CINFO", 1), SERVER_INFO("SINFO", 2),
		ROOM_INFO("RINFO", 2), CLIENT_INVITE("CINV", 3),
		SERVER_INVITE("SINV", 2);

		public String type;
		public int minParams;

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

	public Message(String type) {
		this.type = getMessageTypeByName(type);
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

	public static Message.Type getMessageTypeByName(String name) {
		return typeMap.get(name);
	}

	public static Message parseJSONtoMessage(String json)
			throws JsonParseException, JsonMappingException, IOException {
		// Converts JSON string to HashMap
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> map = new ObjectMapper().readValue(json,
				LinkedHashMap.class);
		// Removes "type" key-value from map and uses returned value to
		// initialise message
		Message message = new Message(map.remove("type"));
		// Sets message map as the remaining map contents
		message.keyValuePairs = map;
		return message;
	}
}
