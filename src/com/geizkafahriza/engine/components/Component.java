package com.geizkafahriza.engine.components;

import com.geizkafahriza.engine.GameObject;

public abstract class Component {

	public boolean isActive = true;

	private GameObject gameObject;
	private Transform transform;
	
	public void setGameObject(GameObject go) {
		gameObject = go;
		transform = gameObject.getTransform();
		init();
	}
	
	protected abstract void init();
	
	public void start() {
		
	}
	
	public boolean getActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive){
		this.isActive = isActive;
	}

	public GameObject getGameObject() {
		return gameObject;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public <T> T getComponent(Class<T> type) {
		return gameObject.<T>getComponent(type);
	}
	
}
