package com.geizkafahriza.engine.physics;

import java.util.List;

import com.geizkafahriza.engine.math.Vector2;

public abstract class CollisionShape {

	private Vector2 worldPosition;
	private Vector2 offset;
	
	public CollisionShape(Vector2 worldPosition, Vector2 offset) {
		setWorldPosition(worldPosition);
		setOffset(offset);
	}
	
	public void setOffset(Vector2 offset) {
		this.offset = offset.duplicate();
	}
	
	public Vector2 getOffset() {
		return offset.duplicate();
	}
	
	protected Vector2 getWorldPosition() {
		return worldPosition.duplicate();
	}

	public void setWorldPosition(Vector2 worldPosition) {
		this.worldPosition = worldPosition.duplicate();
//		System.out.println(worldPosition);
	}
	
	public Vector2 getCenter() {
		Vector2 center = getWorldPosition().plus(getOffset());
		return center;
	}
	
	public abstract boolean checkCollisionWith(CollisionShape other);
	
	protected boolean checkPolyWithAxis(List<Vector2> verticesA, List<Vector2> verticesB, Vector2 normalVector) {
		float minA = Float.POSITIVE_INFINITY, maxA = Float.NEGATIVE_INFINITY; 
		for(Vector2 verticeA : verticesA) {
			float value = verticeA.dot(normalVector);
			if(minA > value) {
				minA = value;
			}
			if(maxA < value) {
				maxA = value;
			}
		}
		float minB = Float.POSITIVE_INFINITY, maxB = Float.NEGATIVE_INFINITY;
		for(Vector2 verticeB : verticesB) {
			float value = verticeB.dot(normalVector);
			if(minB > value) {
				minB = value;
			}
			if(maxB < value) {
				maxB = value;
			}
		}
		boolean inter = (minA <= minB && minB <= maxA)||(minB <= minA && minA <= maxB);
//		if(inter) {
//			System.out.println(minA + " "+maxA+" | "+minB+" "+maxB+" | "+normalVector+" | "+inter);
//		}
		return inter;
	}
	
}
