package mtt.smtt.model;

import java.io.Serializable;
import java.util.List;

import mtt.smtt.model.WorkOrder;

public class RoutSmsWorkOrder extends WorkOrder  implements Serializable {
	
	private static final long serialVersionUID = -4625057449548861836L;
	
		String jsonrpc;
		String id;
		String method;
		RoutSmsParams params;
		public String getJsonrpc() {	return jsonrpc;		}
		public void setJsonrpc(String jsonrpc) { this.jsonrpc = jsonrpc;}
		public String getId() {	return id;	}
		public void setId(String id) {	this.id = id;	}
		public String getMethod() {		return method;	}
		public void setMethod(String method) {	this.method = method;	}
		public RoutSmsParams getParams() {	return params;	}
		public void setParams(RoutSmsParams params) {	this.params = params;	}	
	
	public class RoutSmsParams {		
		List<String> to;
		String text;		
		public List<String> getTo()            {	return to;	      }
		public void setTo(List<String> to)     {	this.to = to;	  }
		public String getText()          {	return text;	  }
		public void setText(String text) {  this.text = text; }		
	}

}
