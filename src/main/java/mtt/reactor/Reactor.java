package mtt.reactor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mtt.smtt.Application;
import mtt.smtt.model.RoutSmsWorkOrder;
import mtt.smtt.model.WorkOrder;
import mtt.reactor.Consumer;
import mtt.reactor.RegisteredWorker;

public class Reactor {
	
	private static BlockingQueue<WorkOrder> workZone = new DelayQueue<>(); // new LinkedBlockingQueue<>();
	private static Map<String, RegisteredWorker> workers;
	//private static List<String> workerNames;
	
	//private String reactorName;
	private String queueName;
	
	RegisteredWorker defaultWorker = new RegisteredWorker() {  // used when no other worker is suitable	
		@Override
		public void work(WorkOrder worder) {
			System.out.println("=(Default worker (inside reactor))=" );
			Application.getInfoTable()
					.get(worder.getOrderId())
					.setStatus("no RegisteredWorker for such method.");			
		}
	};
	   
	public Reactor() {
		workers = new HashMap<String, RegisteredWorker>(); 
		//workerNames = new LinkedList<String>();
		new Consumer(this)
				.setZone(workZone)
				.start();		
	}
	
	public Reactor(String string) {
		this();
		queueName = string;
	}

	public boolean register(String path, RegisteredWorker worker){
		//workerNames == valid methods
		Application.getValidMethods().add(path);
		workers.put(path, worker);
		System.out.println("  ... registered path: " + path + " on [ " + this.queueName + " ]");	
		return true;
		}
	// X-wrapper
	public boolean register(RegisteredWorker worker, String path){		
		return register(path, worker);
		}

	public void execute(WorkOrder workOrder) {		
		String method = workOrder.getMethod();
		System.out.println("Reactor.execute: [ method=" + method + "]" );	
		RegisteredWorker worker = getWorker(method); // ???
		worker.work(workOrder);
	}

/*	
	public static void main(String ... argc) throws InterruptedException {


		//Reactor r = new Reactor(Method method);		
		Consumer consumer = new Consumer(this);		
		consumer.start();
		//new Thread( consumer ).start();
		System.out.println("All threads started.");
		while (true) { 
			Thread.sleep(1000);
			workZone.put(new WorkOrder("directsms")); 
			}
		
	   }
*/

	public void load(WorkOrder workOrder) throws InterruptedException{
		workZone.put(workOrder);
		//System.out.println("...put into work.");
	}
	
	public void loadWithDelay(WorkOrder workOrder, long delay) throws InterruptedException{
		
		// workOrder -> Delayed object
		
		workZone.put(workOrder);
		//System.out.println("...put into work with delay.");
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public RegisteredWorker getWorker(String method) {
		RegisteredWorker ret = null;
		if (Application.getValidMethods().contains(method))
			ret =  workers.get(method);		
		else {
			ret = defaultWorker;
		}
		return ret;
	}

	/*
	public static List<String> getWorkerNames() {
		return workerNames;
	}
	*/
	public String getReactorName() {
		return queueName;
	}

	public void setReactorName(String reactorName) {
		this.queueName = reactorName;
	}
	/*
	public static void setWorkerNames(List<String> workerNames) {
		Reactor.workerNames = workerNames;
	}
	*/
	/*
	public static void setWorkers(Map<String, RegisteredWorker> workers) {
		Reactor.workers = workers;
	}
	*/
}




