package no.ntnu.tdt4240.astrosplit.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.entities.MarineMedicEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineRangeEntity;
import no.ntnu.tdt4240.astrosplit.models.ClassType;
import no.ntnu.tdt4240.astrosplit.models.TeamType;

public class Factory {

	//public Entity create(PooledEngine engine, AssetManager assetManager, Vector2 position, int playerNumber + hp) {
	//TODO: Add hp, movement points and attack points
	public static Entity createEntity(PooledEngine engine, AssetManager assetManager, TeamType teamType, ClassType classType, Vector2 pos, int playerNumber) {
		switch (teamType) {
			case TEAM_GRAYS:
				switch (classType) {
					//case MEDIC: return new GreyMedicEntity(engine, assetManager, teamType, classType, pos, playerNumber);
					//case MELEE: return new GreyMeleeEntity(engine, assetManager, teamType, classType, pos, playerNumber);
					//case RANGE: return new GreyRangedEntity(engine, assetManager, teamType, classType, pos, playerNumber);
					default:
						break;
				}
			case TEAM_MARINES:
				switch (classType) {
					case MEDIC:
						return new MarineMedicEntity().create(engine, assetManager, pos, playerNumber);
					case MELEE:
						return new MarineMeleeEntity().create(engine, assetManager, pos, playerNumber);
					case RANGE:
						return new MarineRangeEntity().create(engine, assetManager, pos, playerNumber);
					default:
						break;
				}
			case TEAM_SECTOIDS:
				switch (classType) {
					//case MEDIC:
					//return new SectoidMedicEntity(engine, assetManager, teamType, classType, pos, playerNumber);
					//case MELEE: return new SectoidMeleeEntity(engine, assetManager, teamType, classType, pos, playerNumber);
					//case RANGE: return new SectoidRangedEntity(engine, assetManager, teamType, classType, pos, playerNumber);
					default:
						break;
				}
			default:
				break;
		}
		return null;
	}
}
