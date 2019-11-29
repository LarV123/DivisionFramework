package com.geizkafahriza.engine.scene;

public class SceneManager {

	private static SceneManager singleton;
	
	public Scene currentScene;
	
	public void LoadScene(Scene scene) {
		currentScene = scene;
		currentScene.Start();
	}
	
	public static SceneManager getInstance() {
		if(singleton == null)
			singleton = new SceneManager();
		return singleton;
	}
}
