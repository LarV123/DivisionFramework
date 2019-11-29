package com.geizkafahriza.engine.physics;

import com.geizkafahriza.engine.GameObject;
import com.geizkafahriza.engine.components.Collider2D;
import com.geizkafahriza.engine.components.Transform;

public class CastInfo {

	private Collider2D col;
	private Transform trans;
	private GameObject go;
	private boolean isHit;
	
	public CastInfo(Collider2D col) {
		isHit = col != null;
		if(col == null)return;
		this.col = col;
		trans = col.getTransform();
		go = trans.getGameObject();
	}
	
	public Collider2D getCollider() {
		return col;
	}
	
	public Transform getTransform() {
		return trans;
	}
	
	public GameObject getGameObject() {
		return go;
	}
	
	public boolean isHit() {
		return isHit;
	}
	
}
