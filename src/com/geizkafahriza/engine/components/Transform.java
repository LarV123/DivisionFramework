package com.geizkafahriza.engine.components;

import com.geizkafahriza.engine.math.Vector2;

public class Transform extends Component {

	private Vector2 position;
	private Vector2 scale;
	private float rotation;
	
	public Transform parent;
	
	public Transform() {
		position = new Vector2(0,0);
		rotation = 0;
		scale = new Vector2(1,1);
	}
	
	public void setScale(Vector2 scale) {
		this.scale = scale.duplicate();
	}
	
	public Vector2 getScale() {
		return scale.duplicate();
	}
	
	public void setPosition(Vector2 pos) {
		position.Insert(pos.duplicate());
	}
	
	public void setLocalPosition(Vector2 pos) {
		position.Insert(parent.position.plus(pos));
	}

	public Vector2 getPosition() {
		return position.duplicate();
	}
	
	public Vector2 getLocalPosition() {
		return position.minus(parent.position).duplicate();
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void setLocalRotation(float rotation) {
		this.rotation = parent.rotation + rotation;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public float getLocalRotation() {
		return rotation - parent.rotation;
	}
	
	public void MovePosition(Vector2 delta) {
		position = position.plus(delta);
	}
	
	public void setTransform(Transform parent) {
		this.parent = parent;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
}
