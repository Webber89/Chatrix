package communication;

import java.io.IOException;

import core.MotherConnection;

public interface InputConnection extends Runnable {
	public void listen() throws IOException;

	public void setInactive();
	
	public void setMother(MotherConnection connection);
}
