package com.geizkafahriza.engine.physics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//import java.util.PriorityQueue;
import java.util.Queue;

import com.geizkafahriza.engine.LayerMask;
import com.geizkafahriza.engine.components.BoxCollider2D;
import com.geizkafahriza.engine.components.Collider2D;
import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Manageable;
import com.geizkafahriza.engine.interfaces.Updateable;
import com.geizkafahriza.engine.math.Vector2;

public class Physics2D extends Manager implements Updateable, Manageable{
	
	private int priority = -500;
	private static Physics2D instance;
	
	public static Physics2D getInstance() {
		if(instance == null)instance = new Physics2D();
		return instance;
	}

	//Collision Layer Mask
	private Map<String, Integer> maskDictionary;
	private LayerMask[] collisionMask;
	private ArrayList<Collider2D> colliders;
	private Queue<CollisionCheck> collisionQueue;
	
	public void setCollisionMask() {
		maskDictionary = new HashMap<String, Integer>();
		collisionMask = new LayerMask[LayerMask.getLayerCount()];
		collisionQueue = new ArrayDeque<CollisionCheck>();
		colliders = new ArrayList<Collider2D>(500);
		int count = 0;
		for(String layerName : LayerMask.getLayerNames()) {
			collisionMask[count] = new LayerMask();
			maskDictionary.put(layerName, count++);
		}
	}
	
	public void addCollisionMask(String layer1, String layer2) {
		int index1 = maskDictionary.get(layer1);
		int index2 = maskDictionary.get(layer2);

		collisionMask[index1].addLayer(layer2);
		collisionMask[index2].addLayer(layer1);
	}
	
	private void addCollider(Collider2D collider) {
		colliders.add(collider);
	}
	
	private void removeCollider(Collider2D collider) {
		colliders.remove(collider);
	}
	
	private boolean checkLayerCollision(Collider2D col1, Collider2D col2) {
		String layerName = LayerMask.LayerToName(col1.getGameObject().getLayer().getLayerValue());
		int layerIndex = maskDictionary.get(layerName);
		boolean check = collisionMask[layerIndex].contain(col2.getGameObject().getLayer().getLayerValue());
		return check;
	}
	
	private boolean checkLayerCollision(LayerMask mask, Collider2D col) {
		boolean check = mask.contain(col.getGameObject().getLayer().getLayerValue());
		return check;
	}
	
	public void update() {
		for(int i = 0; i < colliders.size()-1; i++) {
			Collider2D col1 = colliders.get(i);
			if(!col1.isActive)continue;
			for(int j = i+1; j < colliders.size(); j++) {
				Collider2D col2 = colliders.get(j);
				if(!col2.isActive)continue;
				if(checkLayerCollision(col1, col2)) {
					collisionQueue.add(new CollisionCheck(col1, col2));
				}
			}
		}
		while(collisionQueue.size() > 0) {
			boolean intersect = false;
			CollisionCheck cc = collisionQueue.remove();
			Collider2D c1 = cc.col1;
			Collider2D c2 = cc.col2;
			intersect = c1.getShape().checkCollisionWith(c2.getShape());
			if(intersect) {
				c1.collideWith(c2);
				c2.collideWith(c1);
			}
		}
		for(Collider2D col : colliders) {
			col.finishCollisionCheck();
		}
	}
	
	public CastInfo BoxCast(BoxCollider2D col, Vector2 delta) {
		BoxCollisionShape oldRef = (BoxCollisionShape)col.getShape();
		BoxCollisionShape shape = new BoxCollisionShape(oldRef.getWorldPosition().plus(delta), oldRef.getOffset(), oldRef.getSize());
		for(int i = 0; i < colliders.size(); i++) {
			Collider2D col2 = colliders.get(i);
			if(checkLayerCollision(col, col2) && shape.checkCollisionWith(col2.getShape())) {
				return new CastInfo(col2);
			}
		}
		return new CastInfo(null);
	}
	
	public CastInfo BoxCast(BoxCollider2D col, Vector2 delta, LayerMask mask) {
		BoxCollisionShape oldRef = (BoxCollisionShape)col.getShape();
		BoxCollisionShape shape = new BoxCollisionShape(oldRef.getWorldPosition().plus(delta), oldRef.getOffset(), oldRef.getSize());
		for(int i = 0; i < colliders.size(); i++) {
			Collider2D col2 = colliders.get(i);
			if(checkLayerCollision(mask, col2) && shape.checkCollisionWith(col2.getShape())) {
				return new CastInfo(col2);
			}
		}
		return new CastInfo(null);
	}
	
	public CastInfo BoxCast(Vector2 pos, Vector2 size, LayerMask mask) {
		BoxCollisionShape shape = new BoxCollisionShape(pos, new Vector2(0,0), size);
		for(int i = 0; i < colliders.size(); i++) {
			Collider2D col2 = colliders.get(i);
			if(checkLayerCollision(mask, col2) && shape.checkCollisionWith(col2.getShape())) {
				return new CastInfo(col2);
			}
		}
		return new CastInfo(null);
	}
	
	public CastInfo[] BoxCastAll(Vector2 pos, Vector2 size, LayerMask mask) {
		List<CastInfo> casts = new LinkedList<CastInfo>();
		BoxCollisionShape shape = new BoxCollisionShape(pos, new Vector2(0,0), size);
		for(int i = 0; i < colliders.size(); i++) {
			Collider2D col2 = colliders.get(i);
			if(checkLayerCollision(mask, col2) && shape.checkCollisionWith(col2.getShape())) {
				casts.add(new CastInfo(col2));
			}
		}
		CastInfo[] infos = new CastInfo[casts.size()];
		infos = casts.toArray(infos);
		return infos;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void manage(Manageable object) {
		if(object instanceof Collider2D) {
			addCollider((Collider2D)object);
			super.manage(object);
		}
	}

	@Override
	public void unmanage(Manageable object) {
		if(object instanceof Collider2D) {
			removeCollider((Collider2D)object);
			super.unmanage(object);
		}
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

class CollisionCheck{
	Collider2D col1, col2;
	
	public CollisionCheck(Collider2D col1, Collider2D col2) {
		this.col1 = col1;
		this.col2 = col2;
	}
}