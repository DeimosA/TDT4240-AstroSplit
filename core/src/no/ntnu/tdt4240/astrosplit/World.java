package no.ntnu.tdt4240.astrosplit;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import no.ntnu.tdt4240.astrosplit.views.GameView;

public class World {

	private World() {
		Entity worldEntity = new Entity();
		Engine engine = GameView.getGameEngine();
		engine.addEntity(worldEntity);
	}




}
