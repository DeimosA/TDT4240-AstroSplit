package no.ntnu.tdt4240.astrosplit.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import com.badlogic.gdx.math.Vector2;

//import static no.ntnu.tdt4240.astrosplit.game.abilities.Attack.rangeCheck;

public class MovementSystem extends IteratingSystem {

	private static final Family family =
		Family.all(
			MovementComponent.class,
			PositionComponent.class
		).get();

	private ComponentMapper<MovementComponent> movementMapper;
	private ComponentMapper<PositionComponent> positionMapper;

	public MovementSystem()
	{
		super(family);
		this.movementMapper = ComponentMapper.getFor(MovementComponent.class);
		this.positionMapper = ComponentMapper.getFor(PositionComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		Vector2 currentPosition 	= positionMapper.get(entity).position;
		Vector2 newPosition 		= movementMapper.get(entity).position;
		double range 				= movementMapper.get(entity).distance*32;

		if(newPosition != null)
		{

			if(rangeCheck(currentPosition, newPosition,range))
				currentPosition.set(newPosition);
			newPosition = null;
		}
	}

	private boolean rangeCheck(Vector2 pos1, Vector2 pos2, double range)
	{
		if(pos1.dst(pos2) <= range*Math.sqrt(2))
		{
			return true;
		}
		return false;
	}
}

