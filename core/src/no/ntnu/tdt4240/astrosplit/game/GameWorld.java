package no.ntnu.tdt4240.astrosplit.game;


import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.entities.AlienMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMedicEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineRangeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.SectoidMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.systems.AbilitySystem;
import no.ntnu.tdt4240.astrosplit.game.entities.TargetDummyEntity;
import no.ntnu.tdt4240.astrosplit.game.systems.AbilitySystem;
import no.ntnu.tdt4240.astrosplit.game.entities.TargetDummyEntity;
import no.ntnu.tdt4240.astrosplit.game.systems.MovementSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.RenderingSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.UnitSystem;
import no.ntnu.tdt4240.astrosplit.utils.Assets;


/**
 * Used to initiate entities in the game world
 */
public class GameWorld {


	private PooledEngine engine;
	private AssetManager assetManager;


	public GameWorld(PooledEngine engine, Stage stage, AssetManager assetManager) {
		this.engine = engine;
		this.assetManager = assetManager;

		engine.addSystem(new UnitSystem());
		engine.addSystem(new RenderingSystem(new SpriteBatch(), stage));
		engine.addSystem(new MovementSystem());
		engine.addSystem(new AbilitySystem());

		Assets.loadMarineUnitAssets(assetManager);
		Assets.loadTutorialAssets(assetManager);
		assetManager.finishLoading();
		engine.addSystem(new AbilitySystem());
		Assets.loadMarineUnitAssets(assetManager);
		Assets.loadTutorialAssets(assetManager);
		assetManager.finishLoading();
	}


	//this method should create all units to be shown
	public void create()
	{
		new MarineRangeEntity().create(engine, assetManager, new Vector2(16,16),1);

		new MarineRangeEntity().create(engine, assetManager, new Vector2(-48,16),1);

		new MarineMeleeEntity().create(engine, assetManager, new Vector2(-16,-16),2);

		new TargetDummyEntity().create(engine, assetManager, new Vector2(-16, 16), 2);
	}
}
