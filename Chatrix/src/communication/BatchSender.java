package communication;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.Client;
import server.ServerController;

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
	
	private BatchSender() {
		new Thread(new Pinger()).start();
	}
		
	public void submit(OutputConnection out, Message message) {
		try {
			submit(out, message.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void submit(OutputConnection out, String message) {
		threadPool.submit(new MessageTask(out, message));
	}
	
	public static BatchSender getInstance() {
		return instance;
	}
	
	class Pinger implements Runnable {

		@Override
		public void run() {
			for (Client c : ServerController.getActiveUsers())
				submit(c.output, "ping");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
