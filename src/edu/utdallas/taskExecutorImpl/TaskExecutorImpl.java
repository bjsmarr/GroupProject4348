package edu.utdallas.taskExecutorImpl;
import edu.utdallas.taskExecutor.Task;
import blockingFIFO.*;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.taskExecutor.Queuer;

public class TaskExecutorImpl implements TaskExecutor
{

	private blockingFIFO queue = new blockingFIFO(100);
	private Queuer threadPool[];
	
	public TaskExecutorImpl(int threadPoolSize)
	{
	
		
		this.threadPool = new Queuer[threadPoolSize];
		for(int count = 0; count < threadPoolSize ; count++) {
			String taskName = "TaskThread" + count;
			Queuer newThread = new Queuer(this.queue);
			threadPool[count] = newThread;
			Thread th = new Thread(newThread);
			th.setName(taskName);
			th.start();
			Thread.yield();
		}
	}
	
	@Override
	public void addTask(Task task)
	{
		this.queue.enqueueTask(task);
	}

}
