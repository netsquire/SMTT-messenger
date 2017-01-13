package mtt.smtt;

import java.lang.reflect.Type;

import mtt.smtt.jsonrpc2.JsonRequest;
import mtt.smtt.model.DirectSmsWorkOrder;
import mtt.smtt.model.RoutSmsWorkOrder;
import mtt.smtt.model.StatusWorkOrder;
import mtt.smtt.model.WorkOrder;
import mtt.smtt.model.ZeroSmsWorkOrder;

import com.google.gson.reflect.TypeToken;

public class AppConfiguration {
	
	private Type workOrderType; // = new TypeToken<WorkOrder>() {}.getType();
	private Type directSmsType; // =  new TypeToken<DirectSmsWorkOrder>() {}.getType();
	private Type   routSmsType; // = new TypeToken<RoutSmsWorkOrder>() {}.getType();
	private Type   zeroSmsType; // = new TypeToken<ZeroSmsWorkOrder>() {}.getType();
	private Type    statusType; // = new TypeToken<StatusWorkOrder>() {}.getType();
	private Type jsonRequestType; // = new TypeToken<JsonRequest>() {}.getType();
	
	
	private PropertiesManagement pm;	
	//static String configFile = "resources/config/nc.properties"; // defaults
	
	public AppConfiguration(String file) {		
		//System.out.println("Configuration properties: " + file);
		pm = new PropertiesManagement(file);
		// move to UsedTypes ?
		workOrderType = new TypeToken<WorkOrder>() {}.getType();
		directSmsType = new TypeToken<DirectSmsWorkOrder>() {}.getType();
		routSmsType = new TypeToken<RoutSmsWorkOrder>() {}.getType();
		zeroSmsType = new TypeToken<ZeroSmsWorkOrder>() {}.getType();
		statusType = new TypeToken<StatusWorkOrder>() {}.getType();
		jsonRequestType = new TypeToken<JsonRequest>() {}.getType();
		
	}
	
	public String getProperty(String key){
		return pm.get(key); 
	}	
	public Type getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(Type workOrderType) {
		this.workOrderType = workOrderType;
	}

	public Type getDirectSmsType() {
		return directSmsType;
	}

	public void setDirectSmsType(Type directSmsType) {
		this.directSmsType = directSmsType;
	}

	public Type getRoutSmsType() {
		return routSmsType;
	}

	public void setRoutSmsType(Type routSmsType) {
		this.routSmsType = routSmsType;
	}

	public Type getZeroSmsType() {
		return zeroSmsType;
	}

	public void setZeroSmsType(Type zeroSmsType) {
		this.zeroSmsType = zeroSmsType;
	}

	public Type getStatusType() {
		return statusType;
	}

	public void setStatusType(Type statusType) {
		this.statusType = statusType;
	}

	public Type getJsonRequestType() {
		return jsonRequestType;
	}

	public void setJsonRequestType(Type jsonRequestType) {
		this.jsonRequestType = jsonRequestType;
	}

	public static void main(String[] args) {
		// start logging 
		// load properties		
	}

}
