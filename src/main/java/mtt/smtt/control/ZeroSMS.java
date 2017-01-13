package mtt.smtt.control;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import mtt.smtt.Application;
import mtt.reactor.RegisteredWorker;
import mtt.smtt.model.WorkOrder;
import mtt.smtt.model.ZeroSmsWorkOrder;

public class ZeroSMS extends RegisteredWorker {

Type type = new TypeToken<ZeroSmsWorkOrder>() {}.getType();
	
	@Override
	public void work(WorkOrder worder) {
		System.out.println("ZeroSMS - Got it: " );
		String orderId = worder.getOrderId();
		
		Application.getInfoTable().get(orderId).setStatus("ZeroSMS: you lost. ");		
	}
	
}
