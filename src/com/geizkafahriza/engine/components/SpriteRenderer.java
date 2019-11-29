package com.geizkafahriza.engine.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.GameEngine;
import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.interfaces.Renderable;
import com.geizkafahriza.engine.math.Vector2;
import com.geizkafahriza.engine.sprite.Sprite;

public class SpriteRenderer extends Component implements Renderable{
	
	private int priority;
	private Sprite sprite;
	
	public SpriteRenderer(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void render(Graphics2D g2d) {
		Vector2 drawPos = getGameObject().getTransform().getPosition().minus(new Vector2(sprite.getSize().x * sprite.getPivot().x, sprite.getSize().y * sprite.getPivot().y));
		int posX = (int)(drawPos.x * GameEngine.PIXEL_PER_UNIT);
		int posY = (int)(drawPos.y * GameEngine.PIXEL_PER_UNIT);
		int sizeX = (int)(sprite.getSize().x * GameEngine.PIXEL_PER_UNIT);
		int sizeY = (int)(sprite.getSize().y * GameEngine.PIXEL_PER_UNIT);
		int zeroX = GameEngine.WIDTH/2;
		int zeroY = GameEngine.HEIGHT/2;
		float rotation = getGameObject().getTransform().getRotation();
		
		AffineTransform tx = new AffineTransform();
	    tx.rotate(Math.toRadians(rotation), sprite.getImageSize().x / 2, sprite.getImageSize().y / 2);

	    AffineTransformOp op = new AffineTransformOp(tx,
	        AffineTransformOp.TYPE_BILINEAR);

		g2d.drawImage((Image)op.filter(sprite.getSprite(), null), zeroX+posX, zeroY-posY, zeroX+posX+sizeX, zeroY-posY+sizeY, 0, 0, (int)sprite.getImageSize().x, (int)sprite.getImageSize().y, null);
	}

	@Override
	public void start() {
		
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	protected void init() {
		
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