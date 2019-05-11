package no.ntnu.tdt4240.astrosplit.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttacking;
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
		if(entity.getComponent(ActionComponentAttacking.class).attacking != null){
			attack(entity, entity.getComponent(ActionComponentAttacking.class).attacking);
			entity.getComponent(ActionComponentAttacking.class).attacking = null;

		}

	}

	public void attack(Entity attacker, Entity victim) {
		System.out.println("Pre-attack HP: " + victim.getComponent(HealthComponent.class).health);
		ActionComponentAttack attackComponent = attacker.getComponent(ActionComponentAttack.class);

		if(attacker.getComponent(TypeComponent.class).type == "unit"){
			if (rangeCheck(attacker, victim, attackComponent.range)) {
				HealthComponent health = victim.getComponent(HealthComponent.class);
				ActionComponentAttack action = attacker.getComponent(ActionComponentAttack.class);
				health.health = health.health - action.damage;
			}else {
				System.out.println("Out of range");
			}
		}

		System.out.println("Post-attack HP: " + victim.getComponent(HealthComponent.class).health);
	}

	private boolean rangeCheck(Entity attacker, Entity victim, int range)
	{
		Vector2 attackerPosition = attacker.getComponent(PositionComponent.class).position;
		Vector2 victimPosition = victim.getComponent(PositionComponent.class).position;
		if (victimPosition.dst(attackerPosition) <= range*32) {
			return true;
		}
		return false;
	}

}
