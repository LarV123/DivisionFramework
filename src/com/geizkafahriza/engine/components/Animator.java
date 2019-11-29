package com.geizkafahriza.engine.components;

import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.animation.Animation;
import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Updateable;
public class Animator extends Component implements Updateable{

	private int priority = 0;
	
	private Animation currentAnimation;

	@Override
	public void update() {
		if(currentAnimation != null)
			currentAnimation.update();
	}
	
	public void loadAnimation(Animation anim) {
		currentAnimation = anim;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
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
