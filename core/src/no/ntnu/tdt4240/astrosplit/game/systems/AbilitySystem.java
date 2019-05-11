package no.ntnu.tdt4240.astrosplit.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentHeal;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentTarget;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;

public class AbilitySystem extends IteratingSystem {

	private static final Family family =
		Family.all(
			ActionComponent.class,
			PositionComponent.class
		).one(
			ActionComponentAttack.class,
			ActionComponentHeal.class
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
		if(entity.getComponent(ActionComponentTarget.class).target != null){
			// If Attacker
			if(entity.getComponent(ActionComponentAttack.class) != null){
				attack(entity, entity.getComponent(ActionComponentTarget.class).target);
				entity.getComponent(ActionComponentTarget.class).target = null;
			}
			// If healer
			if(entity.getComponent(ActionComponentHeal.class) != null){
				heal(entity, entity.getComponent(ActionComponentTarget.class).target);
				entity.getComponent(ActionComponentTarget.class).target = null;
			}
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

	public void heal(Entity healer, Entity target) {
		System.out.println("Pre-heal HP: " + target.getComponent(HealthComponent.class).health);
		ActionComponentHeal healComponent = healer.getComponent(ActionComponentHeal.class);

		if(healer.getComponent(TypeComponent.class).type == "unit"){
			if (rangeCheck(healer, target, healComponent.range)) {
				HealthComponent health = target.getComponent(HealthComponent.class);
				ActionComponentHeal heal = healer.getComponent(ActionComponentHeal.class);
				if((health.health + heal.heal) > health.maxHealth){
					health.health = health.maxHealth;
				}else {
					health.health = health.health + heal.heal;
				}
			}else {
				System.out.println("Out of range");
			}
		}

		System.out.println("Post-heal HP: " + target.getComponent(HealthComponent.class).health);
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
