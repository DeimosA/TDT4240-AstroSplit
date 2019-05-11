package no.ntnu.tdt4240.astrosplit.game.factory;

import com.badlogic.ashley.core.Entity;

import no.ntnu.tdt4240.astrosplit.models.ClassType;
import no.ntnu.tdt4240.astrosplit.models.TeamType;

public class Factory {

	public static Entity createEntity(TeamType teamType, ClassType classType) {
		switch (teamType) {
			case TEAM_GRAYS:
				switch (classType) {
					//case MEDIC: return new GreyMedicEntity();
					//case MELEE: return new GreyMeleeEntity();
					//case RANGED: return new GreyRangedEntity();
					default:
						break;
				}
			case TEAM_MARINES:
				switch (classType) {
					//case MEDIC:
					//return new MarineMedicEntity();
					//case MELEE: return new MarineMeleeEntity();
					//case RANGED: return new MarineRangedEntity();
					default:
						break;
				}
			case TEAM_SECTOIDS:
				switch (classType) {
					//case MEDIC:
					//return new SectoidMedicEntity();
					//case MELEE: return new SectoidMeleeEntity();
					//case RANGED: return new SectoidRangedEntity();
					default:
						break;
				}
			default:
				break;
		}
		return null;
	}
}
