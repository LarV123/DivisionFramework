package com.geizkafahriza.engine.ui;

import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.Input;
import com.geizkafahriza.engine.components.Component;
import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Updateable;

public class EventSystem extends Component implements Updateable{

	private List<Button> buttons;
	private int selected = 0;
	
	public EventSystem() {
		buttons = new LinkedList<Button>();
	}
	
	@Override
	protected void init() {
		
	}
	
	public void addButton(Button btn) {
		if(buttons.size() == 0)
			btn.select();
		buttons.add(btn);
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
		while(managers.size() > 0) {
			managers.get(0).unmanage(this);
		}
	}

	@Override
	public void update() {
		if(Input.getInstance().getButtonDown("Down")) {
			selected++;
			if(selected >= buttons.size())
				selected = 0;
			buttons.get(selected).select();
		}
		if(Input.getInstance().getButtonDown("Up")) {
			selected--;
			if(selected < 0)
				selected = buttons.size()-1;
			buttons.get(selected).select();
		}
		if(Input.getInstance().getButtonDown("Submit")) {
			buttons.get(selected).press();
		}
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
