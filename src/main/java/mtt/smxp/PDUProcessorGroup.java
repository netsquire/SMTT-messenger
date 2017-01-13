/*
 * Copyright (c) 1996-2001
 * Logica Mobile Networks Limited
 * All rights reserved.
 *
 * This software is distributed under Logica Open Source License Version 1.0
 * ("Licence Agreement"). You shall use it and distribute only in accordance
 * with the terms of the License Agreement.
 *
 */
package mtt.smxp;

import java.util.Vector;

public class PDUProcessorGroup {
	private Vector<PDUProcessor> processors = null;

	public PDUProcessorGroup() {
		processors = new Vector<PDUProcessor>();
	}
	
	public PDUProcessorGroup(int initSize) {
		processors = new Vector<PDUProcessor>(initSize);
	}

	public void add(PDUProcessor p) {
		synchronized (processors) {
			if (!processors.contains(p)) {
				processors.add(p);
			}
		}
	}

	public void remove(PDUProcessor p) {
		synchronized (processors) {
			processors.remove(p);
		}
	}

	public int count() {
		synchronized (processors) {
			return processors.size();
		}
	}

	public PDUProcessor get(int i) {
		synchronized (processors) {
			return (PDUProcessor) processors.get(i);
		}
	}
}
