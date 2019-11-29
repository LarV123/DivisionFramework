package com.geizkafahriza.engine.physics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.math.Vector2;

public class PolygonCollisionShape extends CollisionShape{

	protected List<Vector2> vertices = new LinkedList<Vector2>();
	
	public PolygonCollisionShape(Vector2 worldPosition, Vector2 offset) {
		super(worldPosition, offset);
	}
	
	public void addVertices(Vector2 vec) {
		vertices.add(vec.duplicate());
	}
	
	public List<Vector2> getVerticesWorldSpace(){
		List<Vector2> verts = new LinkedList<Vector2>();
		Vector2 offset = getOffset();
		Vector2 worldOffset = getWorldPosition().plus(new Vector2(offset.x, offset.y));
		for(Vector2 vert : vertices) {
			verts.add(vert.plus(worldOffset));
		}
		return verts;
	}

	@Override
	public boolean checkCollisionWith(CollisionShape other) {
		boolean intersect = false;
		if(other instanceof PolygonCollisionShape) {
			intersect = checkCollisionPolWithPol(this, (PolygonCollisionShape)other);
		}else if(other instanceof CircleCollisionShape) {
			intersect = checkCollisionPolWithCircle(this, (CircleCollisionShape)other);
		}
		return intersect;
	}
	
	private boolean checkCollisionPolWithPol(PolygonCollisionShape colA, PolygonCollisionShape colB) {
		List<Vector2> verticesA = colA.getVerticesWorldSpace();
		List<Vector2> verticesB = colB.getVerticesWorldSpace();
		if(verticesA.size() > verticesB.size()) {
			List<Vector2> temp = verticesA;
			verticesA = verticesB;
			verticesB = temp;
		}

		Iterator<Vector2> iter = verticesA.iterator();
		Vector2 pointA = iter.next();
		boolean intersect = true;
		while(iter.hasNext() && intersect) {
			Vector2 pointB = iter.next();
			Vector2 normalVector = pointB.minus(pointA).getParallelVector().normalized();
			intersect = checkPolyWithAxis(verticesA, verticesB, normalVector);
			pointA = pointB;
		}
		return intersect;
	}


	private boolean checkCollisionPolWithCircle(PolygonCollisionShape colA, CircleCollisionShape colB) {
		List<Vector2> verticesA, verticesB;
		verticesA = colA.getVerticesWorldSpace();
		verticesB = new LinkedList<Vector2>();
		Vector2 worldPos = colB.getWorldPosition();
		float radius = colB.getRadius();
		Vector2 normalVector = colA.getWorldPosition().minus(colB.getWorldPosition()).normalized();
		verticesB.add(new Vector2(worldPos.minus(normalVector.multiply(radius))));
		verticesB.add(new Vector2(worldPos.plus(normalVector.multiply(radius))));
		boolean intersect = checkPolyWithAxis(verticesA, verticesB, normalVector);
		return intersect;
	}
}
