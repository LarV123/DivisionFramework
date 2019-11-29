package com.geizkafahriza.engine.interfaces;

import java.awt.Graphics2D;

public interface Renderable extends Manageable{
	void render(Graphics2D g2d);
	int getPriority();
}
