package mtt.smtt.control;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import mtt.smtt.Application;
import mtt.reactor.RegisteredWorker;
import mtt.smtt.model.WorkOrder;

public class SingleStatus extends RegisteredWorker {

	Type type = new TypeToken<SingleStatus>() {}.getType();
	
	public void work(WorkOrder order) {
		String smsId  = order.getTag();
		String orderId = order.getOrderId();
		String deliveryStatus = "n/a";
		try {
			deliveryStatus = Application.getSmsPoint().checkStatus(smsId);
		} catch (IOException e) { 
			e.printStackTrace(); 	
			}		
		Application.getInfoTable().get(orderId).setStatus("SMS delivering: " + deliveryStatus);		
	}
}
