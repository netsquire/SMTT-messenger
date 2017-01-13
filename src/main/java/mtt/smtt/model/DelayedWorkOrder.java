package mtt.smtt.model;

import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import mtt.smtt.model.WorkOrder;

public class DelayedWorkOrder extends WorkOrder {

	private static final long serialVersionUID = -500332510783542106L;
	//} class Job implements Delayed {
	//String name;
	String tag;
	String orderId;
	String method;
	private Long releaseTime = System.currentTimeMillis();
	//private Long creationTime = System.currentTimeMillis();
	private long delay = 0;
	private List<String> taskList;
		
	public DelayedWorkOrder(WorkOrder order, int _delay) {
		this.setTag(order.getTag());
		this.setOrderId(order.getOrderId());
		this.setMethod(order.getMethod());
		this.releaseTime += _delay;
		this.delay = _delay;
		}

	@Override
	public int compareTo(Delayed o) {
	    int result = 0;
	    if (this.getDelay(TimeUnit.SECONDS) <= o.getDelay(TimeUnit.SECONDS)) {
	        result = -1;
	    } else
	        result = 1;
	    return result;
	 
	}
	@Override
	public long getDelay(TimeUnit unit) {
	    long delay = this.releaseTime - System.currentTimeMillis(); // + this.delay;
	    return delay;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getReleaseTime() {
		return releaseTime;
	}

	public void setTaskList(List<String> multiTaskId) {
		this.taskList = multiTaskId;
	}

	public List<String> getTaskList() {
		return taskList;
	}
}