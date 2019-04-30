package no.ntnu.tdt4240.astrosplit.game.abilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;

public class Attack {

	public static void attack(Entity attacker, Entity victim, TypeComponent type) {
		System.out.println("Attacker " + attacker);
		System.out.println("Victim " + victim);
		if (type.type.equals("unit")){
			BasicAttack.attack(attacker, victim);
		}
	}

	public static boolean rangeCheck(Entity attacker, Entity victim, int range) {
		Vector2 attackerPosition = attacker.getComponent(PositionComponent.class).position;
		Vector2 victimPosition = victim.getComponent(PositionComponent.class).position;
		if (victimPosition.dst(attackerPosition) <= range) {
			return true;
		}
		return false;
	}
}
