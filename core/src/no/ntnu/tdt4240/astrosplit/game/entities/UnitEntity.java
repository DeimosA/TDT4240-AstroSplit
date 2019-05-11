package no.ntnu.tdt4240.astrosplit.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

public abstract class UnitEntity extends Entity {

	public abstract Entity create(PooledEngine engine,
						   AssetManager assetManager,
						   Vector2 position,
						   int playerNumber);

}
