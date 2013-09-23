package server;

public enum MessageTypes {
	MESSAGE("MSG"), JOIN("JOIN"), QUIT("QUIT"), CREATE("CRT"), INVITE("INV"), INFO("INFO");
	public String type;
	
	private MessageTypes(String type) {
		this.type = type;
	}

}
