package no.ntnu.tdt4240.astrosplit.game;


import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.entities.AlienMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMedicEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineRangeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.SectoidMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.systems.MovementSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.RenderingSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.UnitSystem;


public class GameWorld {


	private PooledEngine engine;


	public GameWorld(PooledEngine engine, Stage stage) {
		this.engine = engine;

		engine.addSystem(new UnitSystem());
		engine.addSystem(new RenderingSystem(new SpriteBatch(), stage));
		engine.addSystem(new MovementSystem());
	}


	//this method should create all units to be shown
	public void create()
	{
		MarineMedicEntity marineMedicEntity = new MarineMedicEntity(engine);
		marineMedicEntity.create(new Vector2(16,16),1);
		MarineMedicEntity marineMedicEntity2 = new MarineMedicEntity(engine);
		marineMedicEntity2.create(new Vector2(-16,-16),2);
		MarineMedicEntity marineMedicEntity3 = new MarineMedicEntity(engine);
		marineMedicEntity3.create(new Vector2(-48,16),1);
	}


//	public void attack(Entity offender, Entity defender)
//	{
//		if(offender.getComponent(ActionComponent.class) != null)
//			offender.getComponent(ActionComponent.class).attackList.add(defender);
//	}


}