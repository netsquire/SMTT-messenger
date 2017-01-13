package mtt.smtt.control;

import java.util.HashMap;

import mtt.reactor.Reactor;

public class ServiceLine extends HashMap<String, Reactor> {

	private static final long serialVersionUID = -2525730560798031336L;

	public Reactor constructOf(Reactor reactor) {
		//System.out.println("Injecting Reactor: " + reactor.getQueueName());
		return put(reactor.getQueueName(), reactor);
	}

}
