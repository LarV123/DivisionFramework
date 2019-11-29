package com.geizkafahriza.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import com.geizkafahriza.engine.handlers.Manager;
import com.geizkafahriza.engine.handlers.Renderer;
import com.geizkafahriza.engine.handlers.Updater;
import com.geizkafahriza.engine.physics.Physics2D;
import com.geizkafahriza.engine.scene.SceneManager;

public class GameEngine extends JPanel implements Runnable {
	
	public static final int PIXEL_PER_UNIT = 50;
	
	private static final long serialVersionUID = 1L;
	
	//panel width
	
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	
	private static List<Manager> managers = new LinkedList<Manager>();
	
	//game update
	private static int updateFrequency = 50;

	private static int maxFramerate = 60;
	
	private static float timeRender;
	private static float timeUpdate;
	
	private boolean isRunning;
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	private Thread Engine;
	private Input input;
	private Physics2D physics;
	
	private Updater updater;
	private Renderer renderer;
	
	private Color background = new Color(8, 79, 0);
	
	public GameEngine() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public synchronized void StartEngine() {
		if(isRunning)return;
		isRunning = true;
		Engine = new Thread(this);
		Engine.start();
	}
	
	public synchronized void StopEngine() {
		if(!isRunning)return;
		isRunning = false;
		System.exit(0);
	}
	
	@Override
	public void run() {
		
		long then = System.currentTimeMillis(), now;
		
		physics = Physics2D.getInstance();
		updater = Updater.getInstance();
		renderer = Renderer.getInstance();

		managers.add(physics);
		managers.add(updater);
		managers.add(renderer);
		
		setEngine();
		
		//in milis
		long updatePeriod = 1000/updateFrequency;
		long renderPeriod = 1000/maxFramerate;
		
		timeUpdate = updatePeriod / 1000f;
		
		float unprocessed = 0;
		
		long lastRenderTime = then + renderPeriod;
		
		long pastTime = System.currentTimeMillis();
		
//		int fps = 0, update = 0;
		
		
		while(isRunning) {
			now = System.currentTimeMillis();
			long delta = now - then;
			then = now;
			unprocessed += (float)delta / updatePeriod;
			
			while (unprocessed >= 1) {
				Update();
//				update++;
				unprocessed--;
			}
			
			if(now - lastRenderTime >= renderPeriod) {
				Render();
//				fps++;
				timeRender = (now-lastRenderTime)/1000f;
				lastRenderTime = now;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(now - pastTime >= 1000) {
//				System.out.printf("FPS : %d | Update %d\n", fps, update);
//				fps = 0;
//				update = 0;
				pastTime = now;
			}
			
		}
	}
	
	private void Update() {
		requestFocus();
		updater.update();
	}
	
	private void Render() {
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setColor(background);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		renderer.render(g);
		g.dispose();
		
		Graphics2D g2d = (Graphics2D)getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}
	
	public static float RenderTime() {
		return timeRender;
	}
	
	public static float UpdateTime() {
		return timeUpdate;
	}
	

	public static int getGameUpdate() {
		return updateFrequency;
	}

	public static int getMaxFramerate() {
		return maxFramerate;
	}

	private static void setGameUpdate(int gameUpdate) {
		if(gameUpdate < 0)gameUpdate = 0;
		updateFrequency = gameUpdate;
	}
	
	private static void setMaxFramerate(int maxFramerate) {
		if(maxFramerate < 0)maxFramerate = 0;
		GameEngine.maxFramerate = maxFramerate;
	}
	
	private void setEngine() {
		setFocusable(true);
		requestFocusInWindow();
		
		
		//setting input
		input = Input.getInstance();
		addKeyListener(input);
		input.addAxis(new InputAxis("Horizontal", KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT));
		input.addAxis(new InputAxis("Vertical", KeyEvent.VK_UP, KeyEvent.VK_DOWN));
		input.addAxis(new InputAxis("Submit", KeyEvent.VK_ENTER, 0));
		input.addAxis(new InputAxis("Place Bomb", KeyEvent.VK_SPACE, 0));
		input.addAxis(new InputAxis("Up", KeyEvent.VK_UP, 0));
		input.addAxis(new InputAxis("Down", KeyEvent.VK_DOWN, 0));
		input.addAxis(new InputAxis("Right", KeyEvent.VK_RIGHT, 0));
		input.addAxis(new InputAxis("Left", KeyEvent.VK_LEFT, 0));
		updater.manage(input);
		
		setGameUpdate(50);
		setMaxFramerate(60);
		
		//setting layers
		LayerMask.addLayerName("Wall");
		LayerMask.addLayerName("Player");
		LayerMask.addLayerName("Power Up");
		LayerMask.addLayerName("UI");
		LayerMask.addLayerName("Bomb");
		LayerMask.addLayerName("Fire");
		physics.setCollisionMask();
		physics.addCollisionMask("Wall", "Player");
		physics.addCollisionMask("Power Up", "Player");
		physics.addCollisionMask("Wall", "Player");
		physics.addCollisionMask("Wall", "Bomb");
		physics.addCollisionMask("Bomb", "Player");
		physics.addCollisionMask("Fire", "Player");
		
		updater.manage(physics);
		
		//load default scene
//		SceneManager.getInstance().LoadScene(new MainMenuScene());
	}
	
	public static List<Manager> getManagers(){
		return managers;
	}
	
}
