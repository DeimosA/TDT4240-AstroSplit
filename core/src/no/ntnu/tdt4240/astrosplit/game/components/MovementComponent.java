package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
	//What kind of movement do we wish to have?
	private int tile_radius = 1;
	public double distance = Math.sqrt(2) * tile_radius;



}
