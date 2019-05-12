package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.enums.ClassType;
import no.ntnu.tdt4240.astrosplit.enums.TeamType;

public class UnitModel {
	public int player;
	public ClassType classType;
	public TeamType teamType;
	public Vector2 pos;
	public int movementPoints;
	public int attackPoints;
	public int hitPoints;

	public UnitModel() {
		System.out.println("Unit created from save!");
	}

	public UnitModel(int player, ClassType classType, TeamType teamType, Vector2 pos, int movementPoints, int attackPoints, int hitPoints) {
		this.player = player;
		this.classType = classType;
		this.teamType = teamType;
		this.pos = pos;
		this.movementPoints = movementPoints;
		this.attackPoints = attackPoints;
		this.hitPoints = hitPoints;
	}
}
