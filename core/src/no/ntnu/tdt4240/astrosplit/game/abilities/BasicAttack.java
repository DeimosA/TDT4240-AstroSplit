package no.ntnu.tdt4240.astrosplit.game.abilities;

import com.badlogic.ashley.core.Entity;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;

public class BasicAttack {

	public static void attack(Entity attacker, Entity victim) {
		ActionComponentAttack attackComponent = attacker.getComponent(ActionComponentAttack.class);
		HealthComponent healthComponent = victim.getComponent(HealthComponent.class);

		if (Attack.rangeCheck(attacker, victim, attackComponent.range)) {
			HealthComponent health = victim.getComponent(HealthComponent.class);
			ActionComponentAttack action = attacker.getComponent(ActionComponentAttack.class);
			health.health = health.health - action.damage;
		}
	}
}
