package com.geizkafahriza.engine.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Manageable;
import com.geizkafahriza.engine.interfaces.Updateable;
import com.geizkafahriza.engine.physics.ColliderEventListener;
import com.geizkafahriza.engine.physics.Collision2D;
import com.geizkafahriza.engine.physics.CollisionShape;

public abstract class Collider2D extends Component implements ColliderEventListener, Updateable, Manageable{

	public boolean isTrigger;
	
	private static final int MAX_COLLISION = 20;
	
	protected CollisionShape shape = null;
	
	private ArrayList<Collider2D> collisions;
	private ArrayList<Collider2D> collisionsOld;

	public Collider2D() {
		collisions = new ArrayList<Collider2D>(MAX_COLLISION);
		collisionsOld = new ArrayList<Collider2D>(MAX_COLLISION);
	}
	
	@Override
	public void update() {
		shape.setWorldPosition(getTransform().getPosition());
	}
	
	public final CollisionShape getShape() {
		return shape;
	}
	
	public final void collideWith(Collider2D other) {
		collisions.add(other);
	}
	
	public final void finishCollisionCheck() {
		Iterator<Collider2D> iter = collisionsOld.iterator();
		while(iter.hasNext()) {
			Collider2D col = iter.next();
			if(!collisions.contains(col)) {
				OnCollisionExit(new Collision2D(col));
				iter.remove();
			}
		}
		for(Collider2D col : collisions) {
			if(collisionsOld.contains(col)) {
				OnCollisionStay(new Collision2D(col));
			}else {
				OnCollisionEnter(new Collision2D(col));
				collisionsOld.add(col);
			}
		}
		collisions.clear();
	}
	
	public int getPriority() {
		return 0;
	}
	
	//manageable
	
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
		while(managers.size() > 0) {
			managers.get(0).unmanage(this);
		}
	}
	
	//events

	ArrayList<ColliderEventListener> eventListeners = new ArrayList<ColliderEventListener>();
	
	public final void addListener(ColliderEventListener listener) {
		if(!eventListeners.contains(listener))
			eventListeners.add(listener);
	}
	
	@Override
	public final void OnCollisionEnter(Collision2D col) {
		// TODO Auto-generated method stub
		for(ColliderEventListener cel : eventListeners) {
			cel.OnCollisionEnter(col);
		}
	}

	@Override
	public final void OnCollisionStay(Collision2D col) {
		// TODO Auto-generated method stub
		for(ColliderEventListener cel : eventListeners) {
			cel.OnCollisionStay(col);
		}
	}

	@Override
	public final void OnCollisionExit(Collision2D col) {
		// TODO Auto-generated method stub
		for(ColliderEventListener cel : eventListeners) {
			cel.OnCollisionExit(col);
		}
	}
	

}
