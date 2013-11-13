package core;


public interface MotherConnection {
	public void gotPing();
	public void inputReceived(String input);

	public void lostConnection();
}
