package no.ntnu.tdt4240.astrosplit.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttacking;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;
import no.ntnu.tdt4240.astrosplit.views.GameView;


public class MarineRangeEntity extends Entity {


	private PooledEngine engine;


	public MarineRangeEntity(PooledEngine engine){
		this.engine = engine;
	}


	public Entity create(Vector2 pos, int player){

		int damage = 20; //Damage of units attack
		int range = 3; //Range of units attack
		int health = 100; //Health of unit
		int movement = 3; //Number of tiles the unit can move
		TextureRegion texture = new TextureRegion(new Texture("Units/marine_ranged.png")); // Texture of the unit
		String type = "unit"; //Type of unit

		ActionComponent ac 					= engine.createComponent(ActionComponent.class);
		ActionComponentAttack aca 			= engine.createComponent(ActionComponentAttack.class);
		ActionComponentAttacking actionComponentAttacking = engine.createComponent(ActionComponentAttacking.class);
		ActorComponent am 					= engine.createComponent(ActorComponent.class);
		HealthComponent hc 					= engine.createComponent(HealthComponent.class);
		MovementComponent mc 				= engine.createComponent(MovementComponent.class);
		PositionComponent pc 				= engine.createComponent(PositionComponent.class);
		TextureComponent tc 				= engine.createComponent(TextureComponent.class);
		TransformComponent tm 				= engine.createComponent(TransformComponent.class);
		TypeComponent tp					= engine.createComponent(TypeComponent.class);
		PlayerComponent playerComponent		= engine.createComponent(PlayerComponent.class);

		aca.damage = damage;
		aca.range = range;
		hc.health = health;
		mc.distance = movement;
		pc.position = pos;
		tc.region = texture;
		tm.scale.set(0.1f,0.1f);
		tp.type = type;
		playerComponent.id = player;

		this.add(actionComponentAttacking);
		this.add(pc);
		this.add(tc);
		this.add(ac);
		this.add(tm);
		this.add(am);
		this.add(tp);
		this.add(hc);
		this.add(mc);
		this.add(aca);
		this.add(playerComponent);
		engine.addEntity(this);

		return this;
	}
}
