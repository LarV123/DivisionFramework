package com.geizkafahriza.engine.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.geizkafahriza.engine.math.Vector2;

public class Sprite {

	private BufferedImage sprite;
	private Vector2 pivot;
	private Vector2 size;
	private int pixelPerUnit = 16;
	
	public Sprite(BufferedImage sprite) {
		this.sprite = sprite;
		pivot = new Vector2(0,0);
	}
	
	public Sprite(BufferedImage sprite, Vector2 pivot) {
		this.sprite = sprite;
		this.pivot = new Vector2(pivot);
	}
	
	public Sprite(String name) {
		try {
			sprite = ImageIO.read(new File(name));
			size = new Vector2(sprite.getWidth(), sprite.getHeight());
			pivot = new Vector2(size.x/2, size.y/2);
		}catch(IOException e) {
			System.out.println("FILE NOT FOUND");
		}
	}
	
	public Sprite(String name, int pixelPerUnit) {
		this.pixelPerUnit = pixelPerUnit;
		try {
			sprite = ImageIO.read(new File(name));
			size = new Vector2(sprite.getWidth(), sprite.getHeight());
			pivot = new Vector2(size.x/2, size.y/2);
		}catch(IOException e) {
			System.out.println("FILE NOT FOUND");
		}
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	public Vector2 getPivot() {
		return new Vector2(pivot.x/pixelPerUnit, pivot.y/pixelPerUnit);
	}
	
	public Vector2 getSize() {
		return new Vector2(size.x/pixelPerUnit, size.y/pixelPerUnit);
	}
	
	public Vector2 getImageSize() {
		return new Vector2(size);
	}
	
	public void setPixelPerUnit(int ppu) {
		pixelPerUnit = ppu;
	}
	
}
