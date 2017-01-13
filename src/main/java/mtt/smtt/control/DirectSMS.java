package mtt.smtt.control;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import mtt.smtt.Application;
import mtt.reactor.Reactor;
import mtt.reactor.RegisteredWorker;
import mtt.smtt.model.DelayedWorkOrder;
import mtt.smtt.model.WorkOrder;

public class DirectSMS extends RegisteredWorker {

	static Type type = new TypeToken<DirectSmsWorkOrder>() {}.getType();
	
	public void work(WorkOrder order) {
		System.out.println("DirectSMS - Got it: " );
		String request = order.getRequest();
		String orderId = order.getOrderId();
		String method = order.getMethod();
		//System.out.println("  Gotten request: " + request);
		DirectSmsWorkOrder dswo = (DirectSmsWorkOrder) Application.getGson().fromJson(request, type);		
		String   to = dswo.getParams().getTo();
		String text = dswo.getParams().getText();
		System.out.println(" sending SMS '" + text + "' -> [" + to + "]");
		String smscTaskId = /* "- like a done..." ; */ Application.getSmsPoint().pushSMS(text, to);		
		order.setTag(smscTaskId);		
		Application.getInfoTable().get(orderId).setStatus("Direct SMS processed (smscId=" + smscTaskId + ")");	
		
		// put into PostProcessing		
		//order.setMethod("postProcessing");
		DelayedWorkOrder delayedOrder = new DelayedWorkOrder(order, 8000);
		delayedOrder.setMethod("singleStatus"); //???
		try {
			Application.getService("post-action").load(delayedOrder);
		} catch (InterruptedException e) {	e.printStackTrace();}
		
	}
	//System.out.println(" JSON workOrder for delayed queue: " + Application.getGson().toJson(order));
	//System.out.println(" Delayed WorkOrder: " + Application.getGson().toJson(delayedOrder));
	//Reactor service0 = Application.getService("dispatch");
	//System.out.println(" Dispatch Reactor: " + Application.getGson().toJson(service0));
	//System.out.println(" Delayed Reactor: " + Application.getGson().toJson(service1));
	
	
	class DirectSmsParams {		
		String to;
		String text;		
		public String getTo()            {	return to;	      }
		public void setTo(String to)     {	this.to = to;	  }
		public String getText()          {	return text;	  }
		public void setText(String text) {  this.text = text; }		
		}

	class DirectSmsWorkOrder {
		String jsonrpc;
		String id;
		String method;
		DirectSmsParams params;
		public String getJsonrpc() {
			return jsonrpc;
		}
		public void setJsonrpc(String jsonrpc) {
			this.jsonrpc = jsonrpc;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public DirectSmsParams getParams() {
			return params;
		}
		public void setParams(DirectSmsParams params) {
			this.params = params;
		}
	}
	
	/*
	public static void main(String... ignored) {
		//String params = "{ \"to\": \"79587154240\",  \"text\": \"Direct SMS - through Reactors\" }";
		
		Type dswoType = new TypeToken<ProxyWorkOrder>() {}.getType();
		String json = "{ \"jsonrpc\": \"2.0\",  \"method\": \"directsms\", \"params\": {   \"to\": \"79587154240\",    \"text\": \"Sent mono sms by Reactor\"   },  \"id\": 33}";
		
		ProxyWorkOrder innerWO = (ProxyWorkOrder) Base.getGson().fromJson(json , dswoType);
		System.out.println("DSWO: " + Base.getGson().toJson(innerWO));
		DirectSmsParams dswo = innerWO.getParams();
		System.out.println("  To: " + dswo.getTo());
		System.out.println("Text: " + dswo.getText());
		
    }
	*/
}	
	/*
	ByteArrayOutputStream pipeOut = new ByteArrayOutputStream();
	PrintStream old_out = System.err;
	System.setErr(new PrintStream(pipeOut));
	*/
	/*
	System.setErr(old_out);
	String output = new String(pipeOut.toByteArray());    	
	order.setFormattedOutput(output);
	*/	

