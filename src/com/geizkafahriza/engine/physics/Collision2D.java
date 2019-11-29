package com.geizkafahriza.engine.physics;

import com.geizkafahriza.engine.components.Collider2D;

public class Collision2D{

	private Collider2D collider;
	
	public Collision2D(Collider2D collider) {
		this.collider = collider;
	}
	
	public Collider2D getCollider() {
		return collider;
	}

}
