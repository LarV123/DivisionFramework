package com.geizkafahriza.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import com.geizkafahriza.engine.components.Component;
import com.geizkafahriza.engine.components.Transform;
import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Manageable;
import com.geizkafahriza.engine.math.Vector2;

public class GameObject {

	//non static
	public String name;
	private Transform transform;
	private LinkedList<Component> components;
	private LayerMask layer;
	
	public GameObject() {
		layer = new LayerMask("Default");
		transform = new Transform();
		components = new LinkedList<Component>();
		addComponent(transform);
	}
	
	public void start() {
		manageComponents();
		for(Component c : components) {
			c.start();
		}
	}
	
	private void manageComponents() {
		for(Manager m : GameEngine.getManagers()) {
			for(Component c : components) {
				if(c instanceof Manageable)
					m.manage((Manageable)c);
			}
		}
	}
	
	public void addComponent(Component component) {
		components.add(component);
		component.setGameObject(this);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<T> type){
//		Class<T> type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Component foundComponent = null;
		for(Component comp: components){
			if(type.isInstance(comp)) {
				foundComponent = comp;
			}
		}
		return (T)foundComponent;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] getComponents(Class<T> type) {
//		Class<T> type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		ArrayList<T> foundComponents = new ArrayList<T>();
		for(Component comp: components){
			if(type.isInstance(comp)) {
				foundComponents.add((T)comp);
			}
		}
		return (T[]) foundComponents.toArray();
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public LayerMask getLayer() {
		return new LayerMask(layer);
	}
	
	public void setLayer(String name) {
		layer = new LayerMask(name);
	}
	
	public void destroy() {
		for(Component c : components) {
			if(c instanceof Manageable) {
				Manageable m = (Manageable)c;
				m.removeFromAllManager();
			}
		}
	}
	
//STATIC 

	public static final int MAX_GAME_OBJECTS = 1000;
	
	private static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>(MAX_GAME_OBJECTS);
	
	public static GameObject FindGameObjectByName(String name) {
		GameObject foundObject = null;
		for(GameObject obj: gameObjects) {
			if(obj.name.equals(name)) {
				foundObject = obj;
				break;
			}
		}
		return foundObject;
	}
	
	public static GameObject[] FindGameObjectsByName(String name) {
		Stack<GameObject> objs = new Stack<GameObject>();
		for(GameObject obj: gameObjects) {
			if(obj.name.equals(name)) {
				objs.add(obj);
			}
		}
		return (GameObject[])objs.toArray();
	}
	
	public static void Destory(GameObject object) {
		object.destroy();
		gameObjects.remove(object);
	}
	
	public static void DestroyAll() {
		gameObjects.clear();
	}
	
	public static void Instantiate(GameObject go, Vector2 pos) {
		go.getTransform().setPosition(pos);
		gameObjects.add(go);
		go.start();
	}
	
}