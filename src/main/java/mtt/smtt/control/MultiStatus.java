package mtt.smtt.control;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.reflect.TypeToken;

import mtt.smtt.Application;
import mtt.reactor.RegisteredWorker;
import mtt.smtt.model.DelayedWorkOrder;
import mtt.smtt.model.WorkOrder;

public class MultiStatus extends RegisteredWorker {

	Type type = new TypeToken<MultiStatus>() {}.getType();
	
	public void work(WorkOrder order) {
		//System.out.println("PostProcessing of request: determining status... " );
		String smsId  = order.getTag();
		String orderId = order.getOrderId();
		String method = order.getMethod();
		if (method.equals("")) {
			;;
			}
		List<String> taskList = ((DelayedWorkOrder) order).getTaskList();
		String deliveryStatus = "n/a";
		StringBuffer status = new StringBuffer();
		for (String task : taskList){
			try {
				deliveryStatus = Application.getSmsPoint().checkStatus(task);
				status.append("(" + task + " - " + deliveryStatus + ")\n");
			} catch (IOException e) { e.printStackTrace(); 	}
		}
		Application.getInfoTable().get(orderId).setStatus("SMS delivering: " + deliveryStatus);
		
		System.out.println(" --> Delivery: " + status);
		/*
		System.out.println(" --> Delay: " + order.getDelay(TimeUnit.SECONDS) + " sec");
		System.out.println(" --> Creation time: " + ((DelayedWorkOrder) order).getReleaseTime());
		System.out.println(" --> Current  time: " + System.currentTimeMillis());
		*/
	}
}
