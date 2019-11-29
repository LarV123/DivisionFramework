package com.geizkafahriza.engine.physics;

public interface ColliderEventListener {

	void OnCollisionEnter(Collision2D col);
	void OnCollisionStay(Collision2D col);
	void OnCollisionExit(Collision2D col);
	
}
