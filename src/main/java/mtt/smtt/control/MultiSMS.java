package mtt.smtt.control;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import mtt.smtt.Application;
import mtt.reactor.RegisteredWorker;
//import mtt.smtt.control.DirectSMS.DirectSmsParams;
import mtt.smtt.model.DelayedWorkOrder;
import mtt.smtt.model.RoutSmsWorkOrder;
import mtt.smtt.model.WorkOrder;

public class MultiSMS extends RegisteredWorker {

	Type type = new TypeToken<RoutSmsWorkOrder>() {}.getType();
	
	@Override
	public void work(WorkOrder worder) {
		System.out.println("MultiSMS - Got it: " );
		String request  = worder.getRequest();
		String orderId = worder.getOrderId();
		RoutSmsWorkOrder rswo = (RoutSmsWorkOrder) Application.getGson().fromJson(request, type);
		
		List<String> toto  = rswo.getParams().getTo();
		String       text  = rswo.getParams().getText();		
		System.out.println("toto: " + toto);
		System.out.println("text: " + text);	
		List<String> multiTaskId = new LinkedList<>();
		StringBuffer preStatus = new StringBuffer( Application.getInfoTable().get(orderId).getStatus() );
		for(String address : toto){
			System.out.println("   ...Sending: {" + text + " -> " + address + "}");
			String taskId = Application.getSmsPoint().pushSMS(text, address);
			System.out.println("   by task: " + taskId);
			preStatus.append(" (ok):" + taskId);
			multiTaskId.add(taskId);
		}		
		
		// put into PostProcessing	(Service "post-action")	
		DelayedWorkOrder delayedOrder = new DelayedWorkOrder(worder, 3000);
		delayedOrder.setMethod("multiStatus");
		delayedOrder.setTaskList(multiTaskId);
		try {
			Application.getService("post-action").load(delayedOrder);
		} catch (InterruptedException e) {	e.printStackTrace();}
		
		Application.getInfoTable().get(orderId).setStatus("MultiSMS: sent. " + preStatus);		
	}
	
}
