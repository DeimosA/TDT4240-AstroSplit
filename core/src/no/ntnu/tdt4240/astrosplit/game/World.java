package no.ntnu.tdt4240.astrosplit.game;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.entities.AlienMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMedicEntity;

import no.ntnu.tdt4240.astrosplit.views.GameView;




public class World {

	private PooledEngine engine;

	public World() {
		this.engine = GameView.getGameEngine();
	}

	//this method should create all units to be shown
	public void create()
	{

		MarineMedicEntity marineMedicEntity = new MarineMedicEntity();
		marineMedicEntity.create(new Vector2(16,16));

	}


	public void attack(Entity offender, Entity defender)
	{
		if(offender.getComponent(ActionComponent.class) != null)
			offender.getComponent(ActionComponent.class).attackList.add(defender);
	}


	public void killUnit(Entity entity)
	{
		engine.removeEntity(entity);
	}


}
