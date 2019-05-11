package no.ntnu.tdt4240.astrosplit.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentTarget;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;
import no.ntnu.tdt4240.astrosplit.utils.Assets;


/**
 * Target dummy for tutorial / practice mode
 */
public class TargetDummyEntity extends UnitEntity {


	public TargetDummyEntity() {

	}


	@Override
	public Entity create(PooledEngine engine, AssetManager assetManager, Vector2 position, int playerNumber) {


		ActionComponent			actionComponent			= engine.createComponent(ActionComponent.class);
		ActionComponentAttack	actionComponentAttack 	= engine.createComponent(ActionComponentAttack.class);

		ActionComponentTarget actionComponentTarget = engine.createComponent(ActionComponentTarget.class);
		MovementComponent		movementComponent 		= engine.createComponent(MovementComponent.class);
		TypeComponent			typeComponent			= engine.createComponent(TypeComponent.class);

		ActorComponent		actorComponent 			= engine.createComponent(ActorComponent.class);
		HealthComponent		healthComponent 		= engine.createComponent(HealthComponent.class);
		PositionComponent	positionComponent 		= engine.createComponent(PositionComponent.class);
		TextureComponent	textureComponent 		= engine.createComponent(TextureComponent.class);
		TransformComponent	transformComponent 		= engine.createComponent(TransformComponent.class);
		PlayerComponent		playerComponent 		= engine.createComponent(PlayerComponent.class);

		healthComponent.health = 200;
		healthComponent.maxHealth = 200;
		positionComponent.position = position;
		textureComponent.region = new TextureRegion(assetManager.get(Assets.unit_targetPractice, Texture.class));
		transformComponent.scale.set(0.1f, 0.1f);
		playerComponent.id = playerNumber; // Always opponent

		this.add(actionComponent);
		this.add(actionComponentAttack);
		this.add(actionComponentTarget);
		this.add(movementComponent);
		this.add(typeComponent);

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
