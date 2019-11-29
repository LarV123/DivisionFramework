package com.geizkafahriza.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Manageable;
import com.geizkafahriza.engine.interfaces.Updateable;

public class Input implements KeyListener, Updateable, Manageable{

	private static Input instance;
	private int priority = -100;
	
	public static Input getInstance() {
		if(instance == null)instance = new Input();
		return instance;
	}
	
	//non static
	
	private Map<Integer, List<ActionAxis>> keyMapping = new LinkedHashMap<Integer, List<ActionAxis>>();
	private Map<Integer, List<InputAxis>> keyAxises = new LinkedHashMap<Integer, List<InputAxis>>();
	private Map<Integer, Boolean> keyState = new LinkedHashMap<Integer, Boolean>();
	private Map<Integer, Boolean> keyStateUpdate = new LinkedHashMap<Integer, Boolean>();
	private Map<String, InputAxis> axises = new LinkedHashMap<String, InputAxis>();
	
	protected void addAxis(InputAxis axis) {
		if(axises.containsKey(axis.name))return;
		
		insertToButton(axis.positiveButton);
		insertToButton(axis.negativeButton);
		
		insertToKeyMapping(axis.positiveButton, axis.positive);
		insertToKeyMapping(axis.negativeButton, axis.negative);
		
		insertToKeyAxis(axis.positiveButton, axis);
		insertToKeyAxis(axis.negativeButton, axis);
		
		axises.put(axis.name, axis);
	}
	
	private void insertToKeyMapping(int keyCode, ActionAxis action) {
		if(!keyMapping.containsKey(keyCode)) {
			List<ActionAxis> actions = new LinkedList<ActionAxis>();
			keyMapping.put(keyCode, actions);
		}
		keyMapping.get(keyCode).add(action);
	}
	
	private void insertToKeyAxis(int keyCode, InputAxis axis) {
		if(!keyAxises.containsKey(keyCode)) {
			List<InputAxis> axises = new LinkedList<InputAxis>();
			keyAxises.put(keyCode, axises);
		}
		keyAxises.get(keyCode).add(axis);
	}
	
	private void insertToButton(int keyCode) {
		if(!keyState.containsKey(keyCode)) {
			keyState.put(keyCode, false);
			keyStateUpdate.put(keyCode, false);
		}
	}
	
	@Override
	public void update() {
		for(Entry<String, InputAxis> ent : axises.entrySet()) {
			ent.getValue().button = false;
			ent.getValue().buttonDown = false;
			ent.getValue().buttonUp = false;
			ent.getValue().resetValue();
		}
		Iterator<Map.Entry<Integer, Boolean>> stateIter = keyState.entrySet().iterator();
		Iterator<Map.Entry<Integer, Boolean>> stateUpdateIter = keyStateUpdate.entrySet().iterator();
		while(stateIter.hasNext()) {
			Map.Entry<Integer, Boolean> entryState = stateIter.next();
			Map.Entry<Integer, Boolean> entryStateUpdate = stateUpdateIter.next();
			int keyCode = entryState.getKey();
			boolean state = entryState.getValue();
			boolean stateUpdate = entryStateUpdate.getValue();
			
			List<InputAxis> axises = keyAxises.get(keyCode);
			
			boolean buttonDown = !stateUpdate && state;
			boolean button = stateUpdate && state;
			boolean buttonUp = stateUpdate && !state;
			for(InputAxis axis : axises) {
				axis.button |= button;
				axis.buttonUp |= buttonUp;
				axis.buttonDown |= buttonDown;
				if(axis.positiveButton == keyCode && (buttonDown || button)) {
					axis.positive.Action();
				}else if(axis.negativeButton == keyCode && (buttonDown || button)){
					axis.negative.Action();
				}
			}
			
			entryStateUpdate.setValue(state);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyState.containsKey(keyCode)) {
			keyState.put(keyCode, true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyState.containsKey(keyCode)) {
			keyState.put(keyCode, false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public float getAxis(String name) {
		return axises.get(name).getAxis();
	}
	
	public boolean getButtonDown(String name) {
		return axises.get(name).getButtonDown();
	}
	
	public boolean getButton(String name) {
		return axises.get(name).getButton();
	}
	
	public boolean getButtonUp(String name) {
		return axises.get(name).getButtonUp();
	}

	@Override
	public int getPriority() {
		return priority;
	}
	
	private List<Manager> managers = new LinkedList<Manager>();

	@Override
	public void managedBy(Manager manager) {
		managers.add(manager);
	}

	@Override
	public void unmanagedBy(Manager manager) {
		managers.remove(manager);
	}

	@Override
	public void removeFromAllManager() {
		for(Manager m : managers) {
			m.unmanage(this);
		}
	}
	
}

interface ActionAxis{

	void Action();
	
}

class InputAxis{
	private int value;
	
	protected ActionAxis positive = new ActionAxis() {
		@Override
		public void Action() {
			if(value == -1)
				value = 0;
			else 
				value = 1;
		}
		
	};
	
	protected ActionAxis negative = new ActionAxis() {
		@Override
		public void Action() {
			if(value == 1)
				value = 0;
			else
				value = -1;
		}
	};
	
	protected void resetValue() {
		value = 0;
	}
	
	protected String name;
	protected int positiveButton;
	protected int negativeButton;
	
	protected boolean buttonDown, button, buttonUp;
	
	public InputAxis(String name, int positiveButton, int negativeButton) {
		this.name = name;
		this.positiveButton = positiveButton;
		this.negativeButton = negativeButton;
	}
	
	public boolean getButtonDown() {
		return buttonDown;
	}
	
	public boolean getButton() {
		return button;
	}
	
	public boolean getButtonUp() {
		return buttonUp;
	}
	
	public float getAxis() {
		return value;
	}
	
}
