package mtt.smtt.model;

import java.io.Serializable;

import mtt.smtt.model.WorkOrder;

public class ZeroSmsWorkOrder 
					extends WorkOrder  
					implements Serializable {

	private static final long serialVersionUID = 4560445567005330195L;
	
	String to;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
}
