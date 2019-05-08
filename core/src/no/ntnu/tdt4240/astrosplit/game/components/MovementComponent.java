package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
	//What kind of movement do we wish to have? -> Manhattan distance?
	public double distance = 60;
	public Vector2 position = null;



}
