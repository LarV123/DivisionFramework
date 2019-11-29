package com.geizkafahriza.engine.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.GameEngine;
import com.geizkafahriza.engine.components.Component;
import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Renderable;
import com.geizkafahriza.engine.math.Vector2;

public class Text extends Component implements Renderable{

	private int priority = 0;
	private String text = "";
	private Color color = Color.black;
	private Font font = new Font("Bebas Neue Regular",Font.PLAIN, 28);
	private float size = 12;
	
	public Text() {
		loadFont("fonts/04B_30__.TTF");
	}
	
	@Override
	protected void init() {
		
	}
	
	private void loadFont(String fName) {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(fName)).deriveFont(12);
		    GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(font);
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
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
	public void render(Graphics2D g2d) {
		Vector2 drawPos = getGameObject().getTransform().getPosition();
		g2d.setFont(font.deriveFont(size));
		g2d.setColor(color);

		int zeroX = GameEngine.WIDTH/2;
		int zeroY = GameEngine.HEIGHT/2;
		int drawPosX = (int)(drawPos.x * GameEngine.PIXEL_PER_UNIT);
		int drawPosY = (int)(drawPos.y * GameEngine.PIXEL_PER_UNIT);
		
		g2d.drawString(text, zeroX+drawPosX, zeroY-drawPosY);
	}

	@Override
	public int getPriority() {
		return priority;
	}

}
