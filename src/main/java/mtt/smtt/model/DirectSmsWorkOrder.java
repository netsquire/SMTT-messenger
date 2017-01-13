package mtt.smtt.model;

import java.io.Serializable;

import mtt.smtt.model.WorkOrder;


public class DirectSmsWorkOrder extends WorkOrder  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String jsonrpc;
	String id;
	String method;
	DirectSmsParams params;
	
	public String getJsonrpc() {return jsonrpc;	}
	public   void setJsonrpc(String jsonrpc) { this.jsonrpc = jsonrpc;}
	public String getId()     {	return id;	}
	public   void setId(String id) {	this.id = id;}
	public String getMethod() {	return method;	}
	public   void setMethod(String method) {	this.method = method;	}
	public DirectSmsParams getParams() {	return params;	}
	public   void setParams(DirectSmsParams params) {	this.params = params;	}

	class DirectSmsParams {		
		String to;
		String text;		
		public String getTo()            {	return to;	      }
		public void setTo(String to)     {	this.to = to;	  }
		public String getText()          {	return text;	  }
		public void setText(String text) {  this.text = text; }		
		}

}