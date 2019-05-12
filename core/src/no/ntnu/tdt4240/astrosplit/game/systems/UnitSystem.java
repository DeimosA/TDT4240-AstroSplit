package no.ntnu.tdt4240.astrosplit.game.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;

/*TODO
	Should try to make this a general unit system later, and find specific info about attributes from type component or inheritance

	All units should be able to:
		-Attack, (use ability)
		-Move
 */
public class UnitSystem extends IteratingSystem {


	//All units have these essential components
	private static final Family family =
		Family.all(
			ActionComponent.class,
			HealthComponent.class,
			MovementComponent.class,
			PositionComponent.class,
			TypeComponent.class
		).get();

	private ComponentMapper<ActionComponent> am;
	private ComponentMapper<HealthComponent> hm;
	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<TypeComponent> tm;


	public UnitSystem() {
		super(family);
		this.am = ComponentMapper.getFor(ActionComponent.class);
		this.hm = ComponentMapper.getFor(HealthComponent.class);
		this.pm = ComponentMapper.getFor(PositionComponent.class);
		this.tm = ComponentMapper.getFor(TypeComponent.class);
	}


	// Iterates over each entity every time the entity system is updated.
	/*
		On update every unit should:
		-update animation e.g. health bar, wind, attack anim
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ActionComponent action 			= am.get(entity);
		HealthComponent health 			= hm.get(entity);
		PositionComponent position 		= pm.get(entity);

		if(health.health <= 0)
			killUnit(entity);
	}

	public void killUnit(Entity entity)
	{
		getEngine().getSystem(RenderingSystem.class).removeActor(entity);
		getEngine().removeEntity(entity);
	}

}
