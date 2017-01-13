package mtt.smtt.jsonrpc2;

import java.lang.reflect.Type;

import mtt.smtt.Application;
import mtt.smtt.jsonrpc2.JsonBasic;
import mtt.smtt.model.StatusWorkOrder;
import mtt.smtt.model.WorkOrder;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JsonRequest extends JsonBasic {

	String id;
	String method;	
	String request;	
	WorkOrder workOrder;
	Class<? extends JsonBasic> paramsType;	
	static Type jsonRequestType = new TypeToken<JsonRequest>() {}.getType();

	public JsonRequest() {}	
	
	public JsonRequest(String id, String json) {
		this.id = id;
		this.request = json;
	}
	
	public JsonRequest(String id, String method, String params) {
		this.id = id;
		this.method = method;
		Type woType = new TypeToken<WorkOrder>() {}.getType();
		this.workOrder = Application.getGson().fromJson(params, woType);
		workOrder.setOrderId(id);
	}
		
	/*
	public static void main(String... ignore) {			
		String jsonString = "{ \"jsonrpc\": \"2.0\", \"method\": \"directsms\", \"params\": { \"to\": \"79587154240\", \"text\": \"Direct SMS - through Reactors\"  },  \"id\": 3 }";
		Type requestType = new TypeToken<JsonRequest>() {}.getType();
		
		System.out.println(" (JsonRequest) Total string: " + jsonString);
		JsonRequest jr = Application.getGson().fromJson(jsonString, requestType);
		String m = jr.getMethod();
		System.out.println(" (JsonRequest) Method: " + m);
	}
	*/
	// -------------------------------------------- //
	
	
	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}
	/*
	public String toString(){
		return Application.getGson().toJson(this.request);
	}
	*/
	
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

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
}

/*//	class Subclass {
//String id;
//String method;	
//DirectSmsWorkOrder params;
//String request;
//
//public String getRequest() {
//	return this.request;
//}
//public String getId() {
//	return id;
//}
//public void setId(String id) {
//	this.id = id;
//}
//public String getMethod() {
//	return method;
//}
//public void setMethod(String method) {
//	this.method = method;
//}
//public DirectSmsWorkOrder getParams() {
//	return params;
//}
//public void setParams(DirectSmsWorkOrder params) {
//	this.params = params;
//}
//public void setRequest(String request) {
//	this.request = request;
//}
//}
*/

/*
//Type minusType = new TypeToken<Minus>() {}.getType();
Type requestType = new TypeToken<Request>() {}.getType();

Request jr = Base.getGson().fromJson(json, requestType);
System.out.println("JSON: id=" + jr.getId());
System.out.println("JSON: jsonrpc=" + jr.getJsonrpc());
System.out.println("JSON: method=" + jr.getMethod());

Minus minus = jr.getParams();		
System.out.println("JSON: minus(params)=" + Base.getGson().toJson( minus));
*/
/*
class Request extends JsonBasic{
	String id;
	String method;	
	Minus params;
	
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
	public Minus getParams() {
		return params;
	}
	public void setParams(Minus params) {
		this.params = params;
	}		
}
*/

/*
class Minus{
	String subtrahend;
	String minuend;
	public String getSubtrahend() {
		return subtrahend;
	}
	public void setSubtrahend(String subtrahend) {
		this.subtrahend = subtrahend;
	}
	public String getMinuend() {
		return minuend;
	}
	public void setMinuend(String minuend) {
		this.minuend = minuend;
	}
}
*/
