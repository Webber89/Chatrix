package core;

@SuppressWarnings("serial")
public class IllegalMessageException extends Exception {

	public IllegalMessageException() {
		super();
	}
	
	public IllegalMessageException(String message) {
		super(message);
	}
}
