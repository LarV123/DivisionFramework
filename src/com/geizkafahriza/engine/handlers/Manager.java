package com.geizkafahriza.engine.handlers;

import com.geizkafahriza.engine.interfaces.Manageable;

public class Manager {

	public void manage(Manageable object) {
		object.managedBy(this);
	}
	public void unmanage(Manageable object) {
		object.unmanagedBy(this);
	}
	
}
