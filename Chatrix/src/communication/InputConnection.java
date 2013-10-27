package communication;

import java.io.IOException;

public interface InputConnection extends Runnable {
	public void listen() throws IOException;

	public void setInactive();
}
