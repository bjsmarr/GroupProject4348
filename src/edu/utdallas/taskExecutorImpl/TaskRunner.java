package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.*;
import edu.utdallas.taskExecutor.Task;



public class TaskRunner implements Runnable{
	
	private BlockingFIFO<Task> TQueue;
	
	public TaskRunner(BlockingFIFO<Task> Queue)
	{
		this.TQueue = Queue;
	}

	@Override
	public void run() {
		
		Task N;
		while(true) {
			
			N = TQueue.take();
			
			try {
				N.execute();
			}catch(Throwable t) {
				
				System.out.println(N.getName());
				System.out.println(t.getMessage());
				System.out.println(t.getStackTrace()+"\n");
				
				
			}
			
		}
		
	}
	
	


		
	}
	
	

}
