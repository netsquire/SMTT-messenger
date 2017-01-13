package mtt.reactor;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
	
	   private final BlockingQueue<String> queue;
	
	   Producer(BlockingQueue<String> q) { 
		   System.out.println("Producer created.");
		   queue = q; 
		   }
	   
	   public void run() {
		   System.out.println("Producer run.");
	     try {
	       while (true) { queue.put(produce()); }
	     } catch (InterruptedException ex) { }
	   }
	      
	   String produce() throws InterruptedException {
		   Thread.sleep(1000);
		   return "mtt.note";
	   }
}