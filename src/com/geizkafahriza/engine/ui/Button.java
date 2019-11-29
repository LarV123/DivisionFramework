package com.geizkafahriza.engine.ui;

import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.components.Component;

public class Button extends Component implements ButtonEventListener{
	
	public Button() {
		eventListeners = new LinkedList<ButtonEventListener>();
	}
	
	@Override
	protected void init() {
		
	}
	
	protected void select() {
		onSelected(this);
	}
	
	protected void press() {
		onPressed(this);
	}
	
	private List<ButtonEventListener> eventListeners;
	
	public void addListener(ButtonEventListener lis) {
		if(!eventListeners.contains(lis))
			eventListeners.add(lis);
	}

	@Override
	public void onPressed(Button btn) {
		for(ButtonEventListener bel : eventListeners) {
			bel.onPressed(btn);
		}
	}

	@Override
	public void onSelected(Button btn) {
		for(ButtonEventListener bel : eventListeners) {
			bel.onSelected(btn);
		}
	}
	

}
