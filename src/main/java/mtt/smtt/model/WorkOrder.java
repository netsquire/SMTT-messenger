package mtt.smtt.model;

import java.io.Serializable;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;



import mtt.smtt.AppConfiguration;
import mtt.smtt.Application;
//import mtt.smtt.Application;
import mtt.smtt.model.RoutSmsWorkOrder;
import mtt.smtt.model.StatusWorkOrder;
import mtt.smtt.model.ZeroSmsWorkOrder;

public class WorkOrder implements Serializable, Delayed {

	private static final long serialVersionUID = -5252743838400035897L;	
	private Type innerParamsType;
	String caseId;
	String requestFrom;
	String request;
	String bag;
	String status = "none";
	Boolean successCode;
	String orderId;
	String formattedOutput;
	String to;
	String by;
	String transport;
	String method;
	String path;
	String tag;
	String text;
	WorkOrder orderParams;
	AppConfiguration config;
	
	public WorkOrder() {}			
	
	public WorkOrder(String _method, String _orderId, String _request) {		
		config = Application.getConfiguration();
		
		//this(_method, _orderId, _params, type);		
		Type type = config.getWorkOrderType();
		if(_method.equals("directsms")) {		type = config.getDirectSmsType();		};
		if(_method.equals("routsms"))   {		type = config.getRoutSmsType();		    };
		if(_method.equals("zerosms"))   {		type = config.getZeroSmsType();		    };
		if(_method.equals("status"))    {		type = config.getStatusType();		    };
		
		this.method  = _method;
		this.orderId = _orderId;
		this.request = _request;
		//System.out.println("(inside Workorder) Params: " + _params);
		this.innerParamsType = type;
		}

	public Type getInnerParamType() {
		return innerParamsType;
	}

	public void setInnerParamType(Class<?> innerParamType) {
		this.innerParamsType = innerParamType;
	}	
	
	public WorkOrder getOrderParams() {
		return orderParams;
	}

	public void setOrderParams(WorkOrder orderParams) {
		this.orderParams = orderParams;
	}

	public Type getWorkOrderType() {
		return config.getWorkOrderType();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public WorkOrder getOrderedParams() {
		return orderParams;
	}

	public void setOrderedParams(WorkOrder orderedParams) {
		this.orderParams = orderedParams;
	}
	public String getBy() {
		return by;
	}
	public void setBy(String by) {
		this.by = by;
	}
	public Boolean getSuccessCode() {
		return successCode;
	}
	public void setSuccessCode(Boolean successCode) {
		this.successCode = successCode;
	}	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}	
	public String getFormattedOutput() {
		return formattedOutput;
	}
	public void setFormattedOutput(String formattedOutput) {
		this.formattedOutput = formattedOutput;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getRequestFrom() {
		return requestFrom;
	}
	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getBag() {
		return bag;
	}
	public void setBag(String bag2) {
		this.bag = bag2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTag() {
		return tag;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setType(Class<?> class1) {
		innerParamsType = class1;
	}

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

}
