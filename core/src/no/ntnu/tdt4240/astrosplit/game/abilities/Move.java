package no.ntnu.tdt4240.astrosplit.game.abilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;

public class Move {

	public static void move(Entity entity, Vector2 position)
	{
		if(entity.getComponent(MovementComponent.class) != null)
		{

			entity.getComponent(MovementComponent.class).position = position;
		}
	}

}
