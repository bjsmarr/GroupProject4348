package edu.utdallas.taskExecutor;
import blockingFIFO.blockingFIFO;

public class Queuer implements Runnable {

	private Task task;
	private blockingFIFO Queue;
	public Queuer(blockingFIFO fIFO) {
		this.Queue = fIFO;
	}
	public void run() {
		while (true) {
			try {
				task = Queue.dequeueTask();
			synchronized (task) {
				task.execute();
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
