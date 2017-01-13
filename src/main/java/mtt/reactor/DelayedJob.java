package mtt.reactor;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedJob {

	static DelayQueue<Job> dQueue = new DelayQueue<>();
	public static int qsize;
	public static int maxQsize = 0;
		
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Start");
		
		Thread thr = new Thread( new DelayedJob().new Reader() ); thr.start();		
		System.out.println("reader running...");
		int number = 100_000;
		for (int k=0; k < number; k++){
			dQueue.offer(new DelayedJob().new Job("week.", (long) (Math.random()*1000)));
			qsize = dQueue.size();
			if(qsize > maxQsize) { maxQsize=qsize; }
			//Thread.sleep((long) (Math.random()));
			}
		/*
		//System.out.println("Job put into queue at " + System.currentTimeMillis());
		dQueue.offer(new DelayedJob().new Job("New day, next week. #2", 3000));		
		//System.out.println("Job put into queue at " + System.currentTimeMillis());
		dQueue.offer(new DelayedJob().new Job("New day, next week. #3", 5000));		
		//System.out.println("Job put into queue at " + System.currentTimeMillis());
		*/
	}

	class Reader implements Runnable {
		@Override
		public void run() {
			System.out.println("     -- Reader started -- ");
			long max=0;
			long min=0;
			long avg=0;
			long sum=0;
			int count=0;
			
			System.out.println("  Deviation max min avg ");
			while (true){
				count++;
				Job job = null;
				try {	
					//System.out.println("<< waiting >>");
					job = dQueue.take();
					//System.out.println("<< fetch >>");
					qsize = dQueue.size();
					if(qsize > maxQsize) { maxQsize=qsize; }
				} catch (InterruptedException e) { 
					e.printStackTrace(); 
					}
				long currentTime = System.currentTimeMillis();
				long actualDelay = currentTime - job.creationTime;
				long deviation = actualDelay - job.delay;
				
				if (deviation > max) {max = deviation;}
				if (deviation < min) {min = deviation;}
				sum += deviation;
				avg = sum / count;
				
				//System.out.println("  <-- Taken: [" + job.name + "] with real delay: " + actualDelay + " (" + deviation + ")");
				System.out.println(" " /* + deviation */ + " " + max + " " + min + " " + avg + " (" +  qsize + " - " + maxQsize + ")");
			}
		}		
	}
	
	class Job implements Delayed {
		String name;
		private Long releaseTime = System.currentTimeMillis();
		private Long creationTime = System.currentTimeMillis();
		private long delay = 0;
		
		public Job(String _name, long _delay){ 
			this.name  = _name;
			this.releaseTime += _delay;
			this.delay = _delay;
			//System.out.println("  --> Created Job '" + this.name +  "' at " + this.releaseTime + " & delay=" + _delay);	
		}
		
		@Override
		public int compareTo(Delayed o) {
		    int result = 0;
		    if (this.getDelay(TimeUnit.SECONDS) <= o.getDelay(TimeUnit.SECONDS)) {
		        result = -1;
		    } else
		        result = 1;
		    return result;
		 
		}
		@Override
		public long getDelay(TimeUnit unit) {
		    long delay = this.releaseTime - System.currentTimeMillis(); // + this.delay;
		    return delay;
		}
	}
}
