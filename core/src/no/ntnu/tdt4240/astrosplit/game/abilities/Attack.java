package no.ntnu.tdt4240.astrosplit.game.abilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;

public class Attack {

	public static void attack(Entity attacker, Entity victim) {
		System.out.println("Pre-attack HP: " + victim.getComponent(HealthComponent.class).health);
		ActionComponentAttack attackComponent = attacker.getComponent(ActionComponentAttack.class);

		if(attacker.getComponent(TypeComponent.class).type == "unit"){
			if (Attack.rangeCheck(attacker, victim, attackComponent.range)) {
				HealthComponent health = victim.getComponent(HealthComponent.class);
				ActionComponentAttack action = attacker.getComponent(ActionComponentAttack.class);
				health.health = health.health - action.damage;
			}else {
				System.out.println("Out of range");
			}
		}

		System.out.println("Post-attack HP: " + victim.getComponent(HealthComponent.class).health);
	}

	public static boolean rangeCheck(Entity attacker, Entity victim, int range) {
		Vector2 attackerPosition = attacker.getComponent(PositionComponent.class).position;
		Vector2 victimPosition = victim.getComponent(PositionComponent.class).position;
		if (victimPosition.dst(attackerPosition) <= range*32) {
			return true;
		}
		return false;
	}
}
