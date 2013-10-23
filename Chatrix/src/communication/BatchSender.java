package communication;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.Message;

public class BatchSender {
	private static BatchSender instance = new BatchSender();
	private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	class MessageTask implements Runnable{
		private OutputConnection out;
		private String message;
		
		public MessageTask(OutputConnection out, String message) {
			this.out = out;
			this.message = message;
		}
		@Override
		public void run() {
			try {
				out.send(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private BatchSender() {}
		
	public void submit(OutputConnection out, Message message) {
		try {
			threadPool.submit(new MessageTask(out, message.toJson()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BatchSender getInstance() {
		return instance;
	}
	
	
	
}
