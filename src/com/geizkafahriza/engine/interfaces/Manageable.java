package com.geizkafahriza.engine.interfaces;

import com.geizkafahriza.engine.handlers.Manager;

public interface Manageable {

	void managedBy(Manager manager);
	void unmanagedBy(Manager manager);
	void removeFromAllManager();
	
}
