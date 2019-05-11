package no.ntnu.tdt4240.astrosplit.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;


/**
 * Target dummy for tutorial / practice mode
 */
public class TargetDummyEntity extends Entity {


	private PooledEngine engine;


	public TargetDummyEntity(PooledEngine engine) {

		this.engine = engine;

	}


	public Entity create(Vector2 pos) {

		TextureRegion texture = new TextureRegion(new Texture("Units/tutorial_scarecrow.png")); // Texture of the unit

		ActorComponent actorComponent 			= engine.createComponent(ActorComponent.class);
		HealthComponent healthComponent 		= engine.createComponent(HealthComponent.class);
		PositionComponent positionComponent 	= engine.createComponent(PositionComponent.class);
		TextureComponent textureComponent 		= engine.createComponent(TextureComponent.class);
		TransformComponent transformComponent 	= engine.createComponent(TransformComponent.class);
		PlayerComponent playerComponent 		= engine.createComponent(PlayerComponent.class);

		healthComponent.health = 300;
		positionComponent.position = pos;
		textureComponent.region = new TextureRegion(new Texture("Units/tutorial_scarecrow.png"));
		transformComponent.scale.set(0.1f,0.1f);
		playerComponent.id = 2; // Always opponent

		this.add(actorComponent);
		this.add(healthComponent);
		this.add(positionComponent);
		this.add(textureComponent);
		this.add(transformComponent);
		this.add(playerComponent);

		engine.addEntity(this);

		return this;
	}



}
