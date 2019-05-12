package no.ntnu.tdt4240.astrosplit.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.entities.GrayMedicEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.GrayMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.GrayRangeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMedicEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.MarineRangeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.SectoidMedicEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.SectoidMeleeEntity;
import no.ntnu.tdt4240.astrosplit.game.entities.SectoidRangeEntity;
import no.ntnu.tdt4240.astrosplit.enums.ClassType;
import no.ntnu.tdt4240.astrosplit.enums.TeamType;

public class UnitFactory {

	//public Entity create(PooledEngine engine, AssetManager assetManager, Vector2 position, int playerNumber + hp) {
	//TODO: Add hp, movement points and attack points
	public static Entity createEntity(PooledEngine engine, AssetManager assetManager, TeamType teamType, ClassType classType, Vector2 pos, int playerNumber) {
		switch (teamType) {
			case TEAM_GRAYS:
				switch (classType) {
					case MEDIC:
						return new GrayMedicEntity().create(engine, assetManager, pos, playerNumber, classType);
					case MELEE:
						return new GrayMeleeEntity().create(engine, assetManager, pos, playerNumber, classType);
					case RANGE:
						return new GrayRangeEntity().create(engine, assetManager, pos, playerNumber, classType);
					default:
						break;
				}
			case TEAM_MARINES:
				switch (classType) {
					case MEDIC:
						return new MarineMedicEntity().create(engine, assetManager, pos, playerNumber, classType);
					case MELEE:
						return new MarineMeleeEntity().create(engine, assetManager, pos, playerNumber, classType);
					case RANGE:
						return new MarineRangeEntity().create(engine, assetManager, pos, playerNumber, classType);
					default:
						break;
				}
			case TEAM_SECTOIDS:
				switch (classType) {
					case MEDIC:
						return new SectoidMedicEntity().create(engine, assetManager, pos, playerNumber, classType);
					case MELEE:
						return new SectoidMeleeEntity().create(engine, assetManager, pos, playerNumber, classType);
					case RANGE:
						return new SectoidRangeEntity().create(engine, assetManager, pos, playerNumber, classType);
					default:
						break;
				}
			default:
				System.err.println("UnitFactory: Could not recognize Team-Class combination");
				break;
		}
		return null;
	}
}
