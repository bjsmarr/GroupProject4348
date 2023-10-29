package blockingFIFO;
import edu.utdallas.taskExecutor.Task;
public class blockingFIFO {

	private Task Queue[];
	private int start, end, count, MaxSize;
	private Object notFull, notEmpty;
	
	public blockingFIFO(int size) {
		this.MaxSize = size;
		this.Queue = new Task[this.MaxSize];
		this.start = 0;
		this.end = 0;
		this.count = 0;
		this.notFull = new Object();
		this.notEmpty = new Object();
	}
	
	public void enqueueTask (Task newTask) {
		
		if (this.count == this.MaxSize) {
			synchronized (notFull) {
				try {
					notFull.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Queue[end] = newTask;
		end = (end+1)% this.MaxSize;
		this.count++;
		
		synchronized (notEmpty) {
			notEmpty.notify();
		}
	}
	
	public Task dequeueTask() {
		if(this.count==0) {
			synchronized (notEmpty) {
				try {
					notEmpty.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Task dequeueTask = this.Queue[start];
		this.start = (this.start+1)%this.MaxSize;
		this.count--;
		synchronized (notFull) {
			notFull.notify();
		}
		return dequeueTask;
	}
}
