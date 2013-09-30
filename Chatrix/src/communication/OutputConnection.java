package communication;

import java.io.IOException;

public interface OutputConnection
{
	public void send(String content) throws IOException;
	
	public void ping();
}
