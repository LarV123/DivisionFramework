package com.geizkafahriza.engine.physics;

import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.math.Vector2;

public class CircleCollisionShape extends CollisionShape{

	private float radius;
	
	public CircleCollisionShape(Vector2 worldPosition, Vector2 offset, float radius) {
		super(worldPosition, offset);
		setRadius(radius);
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}

	@Override
	public boolean checkCollisionWith(CollisionShape other) {
		boolean intersect = false;
		if(other instanceof PolygonCollisionShape) {
			intersect = checkCollisionPolWithCircle((PolygonCollisionShape)other, this);
		}else if(other instanceof CircleCollisionShape) {
			intersect = checkCollisionCircleWithCircle(this, (CircleCollisionShape)other);
		}
		return intersect;
	}
	
	private boolean checkCollisionPolWithCircle(PolygonCollisionShape colA, CircleCollisionShape colB) {
		List<Vector2> verticesA, verticesB;
		verticesA = colA.getVerticesWorldSpace();
		verticesB = new LinkedList<Vector2>();
		Vector2 worldPos = colB.getCenter();
		float radius = colB.getRadius();
		Vector2 normalVector = colA.getCenter().minus(colB.getCenter()).normalized();
		verticesB.add(new Vector2(worldPos.minus(normalVector.multiply(radius))));
		verticesB.add(new Vector2(worldPos.plus(normalVector.multiply(radius))));
		boolean intersect = checkPolyWithAxis(verticesA, verticesB, normalVector);
		return intersect;
	}
	
	private boolean checkCollisionCircleWithCircle(CircleCollisionShape colA, CircleCollisionShape colB) {
		boolean intersect = false;
		float sumOfRadius = colA.getRadius()+colB.getRadius();
		intersect = Vector2.Distance(colA.getCenter(), colB.getCenter()) < sumOfRadius;
		return intersect;
	}
	
}
