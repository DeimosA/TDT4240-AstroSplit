package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.PooledEngine;

public class GameView {

	private static PooledEngine gameEngine = null;

	private GameView() {

	}
	
	public static PooledEngine getGameEngine(){
		if (gameEngine == null){
			gameEngine = new PooledEngine();
		}
		return gameEngine;
	}

}
