package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component, ActionInterface {
	//Distance is tile radius
	public double distance = 1;
	public Vector2 position = null;

	public int movementPoints = 1;
	public int remainingPoints = movementPoints;


	@Override
	public boolean isActionAllowed() {
		return remainingPoints > 0;
	}

	@Override
	public void resetAction() {
		remainingPoints = movementPoints;
	}
}
