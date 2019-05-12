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
import no.ntnu.tdt4240.astrosplit.models.TeamType;
import no.ntnu.tdt4240.astrosplit.utils.Assets;


public class GrayMedicEntity extends UnitEntity {



	public GrayMedicEntity(){

	}


	@Override
	public Entity create(
		PooledEngine engine,
		AssetManager assetManager,
		Vector2 position,
		int playerNumber,
		ClassType unitClassType
	) {

		int heal = 75; //Damage of units attack
		int range = 1; //Range of units attack
		int health = 250; //Health of unit
		int movement = 3; //Number of tiles the unit can move

		ActionComponent ac 								= engine.createComponent(ActionComponent.class);
		ActionComponentHeal actionComponentHeal 		= engine.createComponent(ActionComponentHeal.class);
		ActionComponentTarget actionComponentTarget 	= engine.createComponent(ActionComponentTarget.class);
		ActorComponent am 								= engine.createComponent(ActorComponent.class);
		HealthComponent hc 								= engine.createComponent(HealthComponent.class);
		MovementComponent mc 							= engine.createComponent(MovementComponent.class);
		PositionComponent pc 							= engine.createComponent(PositionComponent.class);
		TextureComponent tc 							= engine.createComponent(TextureComponent.class);
		TransformComponent tm 							= engine.createComponent(TransformComponent.class);
		TypeComponent tp								= engine.createComponent(TypeComponent.class);
		PlayerComponent playerComponent 				= engine.createComponent(PlayerComponent.class);

		actionComponentHeal.heal = heal;
		actionComponentHeal.range = range;
		hc.health = health;
		hc.maxHealth = health;
		mc.distance = movement;
		pc.position = position;
		tc.region = new TextureRegion(assetManager.get(Assets.unit_gray_medic, Texture.class));
		tm.scale.set(0.1f,0.1f);
		tp.unitClassType = unitClassType;
		tp.teamType = TeamType.TEAM_GRAYS;
		playerComponent.id = playerNumber;

		this.add(actionComponentTarget);
		this.add(pc);
		this.add(tc);
		this.add(ac);
		this.add(tm);
		this.add(am);
		this.add(tp);
		this.add(hc);
		this.add(mc);
		this.add(actionComponentHeal);
		this.add(playerComponent);

		engine.addEntity(this);

		return this;
	}
}
