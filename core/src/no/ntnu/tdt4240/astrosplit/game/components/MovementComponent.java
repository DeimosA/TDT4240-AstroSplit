package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
	//Distance is tile radius
	public double distance = 1;
	public Vector2 position = null;
	public int movementPoints = 1;


}
