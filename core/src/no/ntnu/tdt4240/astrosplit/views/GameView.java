package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.Engine;

public class GameView {

	private static Engine gameEngine = null;

	private GameView() {

	}
	
	public static Engine getGameEngine(){
		if (gameEngine == null){
			gameEngine = new Engine();
		}
		return gameEngine;
	}

}
