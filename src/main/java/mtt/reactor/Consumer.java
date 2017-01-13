package mtt.reactor;

import mtt.reactor.AbstractConsumer;
import mtt.reactor.Reactor;
import mtt.smtt.model.WorkOrder;

public class Consumer extends AbstractConsumer {

	Reactor parent;
	
	public Consumer(Reactor reactor) {
		this.parent = reactor;
	}
	
	@Override
	public void consume(WorkOrder workOrder) {
		System.out.println("Consume: got '" + workOrder.getMethod() + "'");
		parent.execute(workOrder);
		
	}
	

}
