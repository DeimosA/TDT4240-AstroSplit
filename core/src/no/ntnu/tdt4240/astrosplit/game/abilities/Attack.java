package no.ntnu.tdt4240.astrosplit.game.abilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;

public class Attack {

	public static void attack(Entity attacker, Entity victim) {
		System.out.println("Attacker " + attacker);
		System.out.println("Victim " + victim);
		System.out.println("Pre-attack HP: " + victim.getComponent(HealthComponent.class).health);

		if(attacker.getComponent(TypeComponent.class).type == "unit")
			BasicAttack.attack(attacker, victim);
		
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
