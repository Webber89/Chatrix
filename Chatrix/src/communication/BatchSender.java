package communication;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.Client;
import server.ServerController;

import core.Message;

public class BatchSender {
	private static BatchSender instance = new BatchSender();
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(10);

	class MessageTask implements Runnable {
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
				System.out.println("Could not send, client disconnected");
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
			while (true) {
				for (Client c : ServerController.getActiveUsers()) {
					long time = System.currentTimeMillis();
					if (!c.isActive() || (time - c.getLastPing())>2000) {
						c.setActive(false);
						System.out.println("Batchsender set " + c.getName() + " as inactive");
					} else {
						submit(c.output, "ping");
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
