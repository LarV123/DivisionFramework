package com.geizkafahriza.engine.components;

import com.geizkafahriza.engine.math.Vector2;
import com.geizkafahriza.engine.physics.BoxCollisionShape;

public class BoxCollider2D extends Collider2D{
	Vector2 size, offset;
	public BoxCollider2D(Vector2 offset, Vector2 size) {
		this.size = size.duplicate();
		this.offset = offset.duplicate();
	}
	
	public void setSize(Vector2 size) {
		((BoxCollisionShape)shape).setSize(size);
	}
	
	public void setOffset(Vector2 offset) {
		((BoxCollisionShape)shape).setOffset(offset);
	}
	
	public Vector2 getSize() {
		return ((BoxCollisionShape)shape).getSize();
	}
	
	public Vector2 getOffset() {
		return ((BoxCollisionShape)shape).getOffset();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		shape = new BoxCollisionShape(getTransform().getPosition(), offset, size);
	}
	
}
