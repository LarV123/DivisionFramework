package com.geizkafahriza.engine.animation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.geizkafahriza.engine.components.SpriteRenderer;
import com.geizkafahriza.engine.sprite.Sprite;

public class Animation {

	private SpriteRenderer renderer;
	
	private List<Frame> frames = new LinkedList<Frame>();
	private int curFrameCount = 0;
	private Iterator<Frame> curFrameIter;
	private Frame curFrame;
	
	public Animation(SpriteRenderer renderer) {
		this.renderer = renderer;
	}
	
	public void update() {
		if(frames.size() <= 0)return;
		if(curFrameCount == 0) {
			if(!curFrameIter.hasNext()) {
				curFrameIter = frames.iterator();
			}
			curFrame = curFrameIter.next();
			curFrameCount = curFrame.frameLength;
		}
		renderer.setSprite(curFrame.sprite);
		curFrameCount--;
	}
	
	public void addFrame(Sprite sprite, int frameLength) {
		Frame newFrame = new Frame(sprite, frameLength);
		frames.add(newFrame);
		curFrameIter = frames.iterator();
	}

	class Frame{
		protected Sprite sprite;
		protected int frameLength;
		
		public Frame(Sprite sprite, int frameLength) {
			this.sprite = sprite;
			this.frameLength = frameLength;
		}
		
	}
	
}