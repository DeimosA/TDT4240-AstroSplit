package no.ntnu.tdt4240.astrosplit.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;

public class AbilitySystem extends IteratingSystem {

	private static final Family family =
		Family.all(
			ActionComponent.class,
			ActionComponentAttack.class,
			PositionComponent.class
		).get();

	private ComponentMapper<ActionComponent> actionMapper;
	private ComponentMapper<ActionComponentAttack> attackMapper;
	private ComponentMapper<PositionComponent> positionMapper;

	public AbilitySystem()
	{
		super(family);
		this.actionMapper = ComponentMapper.getFor(ActionComponent.class);
		this.attackMapper = ComponentMapper.getFor(ActionComponentAttack.class);
		this.positionMapper = ComponentMapper.getFor(PositionComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ArrayList<Entity> attackList = actionMapper.get(entity).attackList;
		if(attackList.size() != 0){
			System.out.println("Attacks");
		}
	}

	public void attack(Entity attacker, Entity victim) {
		System.out.println("Pre-attack HP: " + victim.getComponent(HealthComponent.class).health);
		ActionComponentAttack attackComponent = attacker.getComponent(ActionComponentAttack.class);

		if(attacker.getComponent(TypeComponent.class).type == "unit"){
			if (rangeCheck(attacker, victim)) {
				HealthComponent health = victim.getComponent(HealthComponent.class);
				ActionComponentAttack action = attacker.getComponent(ActionComponentAttack.class);
				health.health = health.health - action.damage;
			}else {
				System.out.println("Out of range");
			}
		}

		System.out.println("Post-attack HP: " + victim.getComponent(HealthComponent.class).health);
	}

	private boolean rangeCheck(Entity attacker, Entity victim)
	{
		Vector2 pos1 = positionMapper.get(attacker).position;
		Vector2 pos2 = positionMapper.get(victim).position;
		double range = attackMapper.get(attacker).range;
		if(pos1.dst(pos2) <= range*Math.sqrt(2))
		{
			return true;
		}
		return false;
	}

}
