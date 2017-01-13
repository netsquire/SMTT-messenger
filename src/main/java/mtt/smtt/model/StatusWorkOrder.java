package mtt.smtt.model;

import mtt.smtt.model.WorkOrder;

public class StatusWorkOrder extends WorkOrder  {

	private static final long serialVersionUID = 3286654711538591734L;

	String taskId;
	String delivery;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	
}
