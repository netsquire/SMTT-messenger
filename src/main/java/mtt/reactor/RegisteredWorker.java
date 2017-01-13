package mtt.reactor;

import mtt.smtt.Application;
import mtt.smtt.model.WorkOrder;

public class RegisteredWorker {

	String totem = "=(Default worker (inside reactor))=";
	
	public void work(WorkOrder worder) {
		System.out.println(getTotem());
		Application.getInfoTable()
				.get(worder.getOrderId())
				.setStatus("no worker for such method. (default worker)");			
	}

	public String getTotem() {
		return totem;
	}

	public void setTotem(String totem) {
		this.totem = totem;
	}
	
}
