package com.geizkafahriza.engine.math;

public class Vector2 {

	public float x, y;
	
	public Vector2() {
		x = 0;
		y = 0;
	}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2( Vector2 src ) {
		x = src.x;
		y = src.y;
	}
	
	public void Insert (Vector2 src) {
		x = src.x;
		y = src.y;
	}
	
	public void Insert (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void Normalize() {
		if(x == 0 && y == 0)return;
		float dis =  magnitude();
		x /= dis;
		y /= dis;
	}
	
	public Vector2 normalized() {
		if(x == 0 && y == 0)return duplicate();
		float dis = magnitude();
		return new Vector2(x/dis, y/dis);
	}
	
	public float magnitude() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public Vector2 plus(Vector2 other) {
		if(other == null)return new Vector2(this);
		return new Vector2(x+other.x, y+other.y);
	}
	
	public Vector2 minus(Vector2 other) {
		if(other == null)return new Vector2(this);
		return new Vector2(x-other.x, y-other.y);
	}
	
	public Vector2 multiply(float other) {
		return new Vector2(x * other, y * other);
	}
	
	public float dot(Vector2 other) {
		return x*other.x + y*other.y;
	}
	
	public Vector2 negate() {
		return new Vector2(-x+0.0f, -y);
	}
	
	public boolean equals(Object other) {
		if(other == null)return false;
		if(!(other instanceof Vector2))return false;
		if(((Vector2)other).x != x)return false;
		if(((Vector2)other).y != y)return false;
		return true;
		
	}
	
	public Vector2 duplicate() {
		return new Vector2(this);
	}
	
	public Vector2 getParallelVector() {
		return new Vector2(-y, x);
	}

	@Override
	public String toString() {
		return "Vector2 (" + x + ", " + y + ")";
	}
	
	//static
	
	public static Vector2 LerpClamped(Vector2 a, Vector2 b, float v) {
		if(v > 1f)v = 1;
		else if(v < 0)v = 0;
		float distX = b.x-a.x;
		float distY = b.y-a.y;
		return new Vector2(a.x+distX*v, a.y+distY*v);
	}
	
	public static float Distance(Vector2 a, Vector2 b) {
		Vector2 delta = b.minus(a);
		return delta.magnitude();
	}
	
	public static VectorOrientation getOrientation(Vector2 a, Vector2 b, Vector2 c) {
		float val = (b.y - a.y) * (c.x - b.x) - 
	              (b.x - a.x) * (c.y - b.y);
		if(val == 0)return VectorOrientation.Colinear;
		return (val > 0 ? VectorOrientation.Clockwise : VectorOrientation.CounterClockwise);
	}
	
}
