package no.ntnu.tdt4240.astrosplit.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentHeal;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentTarget;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;
import no.ntnu.tdt4240.astrosplit.models.ClassType;
import no.ntnu.tdt4240.astrosplit.utils.Assets;


public class MarineMedicEntity extends UnitEntity {



	public MarineMedicEntity(){

	}


	@Override
	public Entity create(
		PooledEngine engine,
		AssetManager assetManager,
		Vector2 position,
		int playerNumber,
		ClassType unitClassType
	) {

		int damage = 50; //Damage of units attack
		int range = 2; //Range of units attack
		int health = 100; //Health of unit
		int movement = 3; //Number of tiles the unit can move

		ActionComponent actionComponent					= engine.createComponent(ActionComponent.class);
		ActionComponentTarget actionComponentTarget 	= engine.createComponent(ActionComponentTarget.class);
		ActionComponentHeal actionComponentHeal 		= engine.createComponent(ActionComponentHeal.class);
		ActorComponent actorComponent 					= engine.createComponent(ActorComponent.class);
		HealthComponent healthComponent 				= engine.createComponent(HealthComponent.class);
		MovementComponent movementComponent 			= engine.createComponent(MovementComponent.class);
		PositionComponent positionComponent 			= engine.createComponent(PositionComponent.class);
		TextureComponent textureComponent 				= engine.createComponent(TextureComponent.class);
		TransformComponent transformComponent 			= engine.createComponent(TransformComponent.class);
		TypeComponent typeComponent						= engine.createComponent(TypeComponent.class);
		PlayerComponent playerComponent 				= engine.createComponent(PlayerComponent.class);


		healthComponent.health = health;
		healthComponent.maxHealth = health;
		actionComponentHeal.range = range;
		movementComponent.distance = movement;
		positionComponent.position = position;
		textureComponent.region = new TextureRegion(assetManager.get(Assets.unit_marine_medic, Texture.class));
		transformComponent.scale.set(0.1f,0.1f);
		typeComponent.unitClassType = unitClassType;
		playerComponent.id = playerNumber;

		this.add(actionComponentTarget);
		this.add(positionComponent);
		this.add(textureComponent);
		this.add(actionComponent);
		this.add(transformComponent);
		this.add(actorComponent);
		this.add(typeComponent);
		this.add(healthComponent);
		this.add(movementComponent);
		this.add(actionComponentHeal);
		this.add(playerComponent);

		engine.addEntity(this);

		return this;
	}
}
