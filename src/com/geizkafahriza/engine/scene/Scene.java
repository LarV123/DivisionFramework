package com.geizkafahriza.engine.scene;

import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.GameObject;
import com.geizkafahriza.engine.handlers.Updater;

public abstract class Scene {

	private List<GameObject> rootGameObjects;
	private Updater updater;
	
	public Scene() {
		rootGameObjects = new LinkedList<GameObject>();
	}
	
	public abstract void Start();
	
	public void Destroy() {
		rootGameObjects.clear();
	}
	
	public void addGameObject(GameObject go) {
		if(!rootGameObjects.contains(go))
			rootGameObjects.add(go);
	}
	
	public List<GameObject> getRootGameObjects() {
		return new LinkedList<GameObject>(rootGameObjects);
	}
	
}
