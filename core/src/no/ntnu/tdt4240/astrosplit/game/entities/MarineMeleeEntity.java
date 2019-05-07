package no.ntnu.tdt4240.astrosplit.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;
import no.ntnu.tdt4240.astrosplit.views.GameView;


public class MarineMeleeEntity {

	private PooledEngine engine;

	public MarineMeleeEntity(){engine = GameView.getGameEngine();}

	public Entity create(Vector2 pos){

		Entity entity = new Entity();


		int damage = 50; //Damage of units attack
		int range = 100; //Range of units attack
		int health = 300; //Health of unit
		int movement = 3; //Number of tiles the unit can move
		TextureRegion texture = new TextureRegion(new Texture("Units/marine_melee.png")); // Texture of the unit
		String type = "unit"; //Type of unit

		ActionComponent ac 			= engine.createComponent(ActionComponent.class);
		ActionComponentAttack aca 	= engine.createComponent(ActionComponentAttack.class);
		ActorComponent am 			= engine.createComponent(ActorComponent.class);
		HealthComponent hc 			= engine.createComponent(HealthComponent.class);
		MovementComponent mc 		= engine.createComponent(MovementComponent.class);
		PositionComponent pc 		= engine.createComponent(PositionComponent.class);
		TextureComponent tc 		= engine.createComponent(TextureComponent.class);
		TransformComponent tm 		= engine.createComponent(TransformComponent.class);
		TypeComponent tp			= engine.createComponent(TypeComponent.class);

		aca.damage = damage;
		aca.range = range;
		hc.health = health;
		mc.distance = movement;
		pc.position = pos;
		tc.region = texture;
		tm.scale.set(0.1f,0.1f);
		tp.type = type;

		entity.add(pc);
		entity.add(tc);
		entity.add(ac);
		entity.add(tm);
		entity.add(am);
		entity.add(tp);
		entity.add(hc);
		entity.add(mc);
		entity.add(aca);
		engine.addEntity(entity);

		return entity;

	}
}
