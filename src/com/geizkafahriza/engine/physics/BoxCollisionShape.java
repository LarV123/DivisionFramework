package com.geizkafahriza.engine.physics;

import com.geizkafahriza.engine.math.Vector2;

public class BoxCollisionShape extends PolygonCollisionShape{

	private Vector2 size;
	
	public BoxCollisionShape(Vector2 worldPosition, Vector2 offset, Vector2 size) {
		super(worldPosition, offset);
		setSize(size);
		addVertices(offset.plus(new Vector2(-size.x/2, size.y/2)));
		addVertices(offset.plus(new Vector2(size.x/2, size.y/2)));
		addVertices(offset.plus(new Vector2(size.x/2, -size.y/2)));
		addVertices(offset.plus(new Vector2(-size.x/2, -size.y/2)));
		addVertices(offset.plus(new Vector2(-size.x/2, size.y/2)));
	}
	
	public void setSize(Vector2 size) {
		this.size = size.duplicate();
	}
	
	public Vector2 getSize() {
		return size.duplicate();
	}

}
