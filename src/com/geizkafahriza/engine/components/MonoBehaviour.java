package com.geizkafahriza.engine.components;

import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.GameObject;
import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Updateable;
import com.geizkafahriza.engine.physics.Collision2D;

public abstract class MonoBehaviour extends Component implements Updateable {
	
	private int priority = 0;
	
	public abstract void Start();
	public abstract void FixedUpdate();
	
	public void init() {
		
	}
	
	public void start() {
		Start();
	}
	
	@Override
	public void update() {
		FixedUpdate();
	}
	
	public void onTriggerEnter2D(Collision2D other) {
		
	}
	
	public void onTriggerStay2D(Collision2D other) {
		
	}
	
	public void onTriggerExit2D(Collision2D other) {
		
	}
	
	public void destroy(GameObject gameObject) {
		GameObject.Destory(gameObject);
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}
	
	private List<Manager> managers = new LinkedList<Manager>();

	@Override
	public void managedBy(Manager manager) {
		managers.add(manager);
	}

	@Override
	public void unmanagedBy(Manager manager) {
		managers.remove(manager);
	}

	@Override
	public void removeFromAllManager() {
		for(Manager m : managers) {
			m.unmanage(this);
		}
	}
	
}
