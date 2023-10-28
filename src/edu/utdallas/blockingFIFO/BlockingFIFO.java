package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFO<T> {

    private final Object[] buffer;
    private int nextIn;
    private int nextOut;
    private int count;
    private final Object notFull;
    private final Object notEmpty;

    public BlockingFIFO(int capacity) {
        buffer = new Object[capacity];
        nextIn = 0;
        nextOut = 0;
        count = 0;
        notFull = new Object();
        notEmpty = new Object();

       
    }

    private boolean isMaxTasksExceeded() {
		
    	return count > 100;
		
	}

	public void put(Task task) throws InterruptedException {
        synchronized (notFull) {
        	
        	 if (isMaxTasksExceeded()) {
                 
        		 throw new IllegalArgumentException("Capacity must be 100 or less.");
             }
        	
        	
            while (count == buffer.length) {
                notFull.wait();
            }
            buffer[nextIn] = task;
            nextIn = (nextIn + 1) % buffer.length;
            count++;
            synchronized (notEmpty) {
                notEmpty.notify();
            }
        }
    }

    public Task take() throws InterruptedException {
        synchronized (notEmpty) {
            while (count == 0) {
                notEmpty.wait();
            }
            @SuppressWarnings("unchecked")
            Task result = (Task) buffer[nextOut];
            nextOut = (nextOut + 1) % buffer.length;
            count--;
            synchronized (notFull) {
                notFull.notify();
            }
            return result;
        }
    }
}