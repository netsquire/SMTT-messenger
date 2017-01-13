package mtt.reactor;

import java.util.concurrent.BlockingQueue;

import mtt.smtt.model.WorkOrder;


public abstract class AbstractConsumer implements Runnable {	   
	
	public abstract void consume(WorkOrder workOrder);
	
	private BlockingQueue<WorkOrder> queue;		
	
	AbstractConsumer setZone(BlockingQueue<WorkOrder> workZone)	{ 
		this.queue = workZone; 
		return this;
		}	
	
	public void run() {
	     try {
	       while (true) { 
	    	   consume(queue.take()); 
	    	   }
	       } catch (InterruptedException ex) {}
	   }

	AbstractConsumer start(){		
		new Thread(this).start();
		return this;
	}
}