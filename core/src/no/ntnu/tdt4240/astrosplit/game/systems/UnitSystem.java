package no.ntnu.tdt4240.astrosplit.game.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import no.ntnu.tdt4240.astrosplit.game.World;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;

import com.badlogic.gdx.math.Vector2;

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

	private World world;

	private ComponentMapper<ActionComponent> am;
	private ComponentMapper<HealthComponent> hm;
	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<TypeComponent> tm;

	public UnitSystem(World world) {
		super(family);
		this.am = ComponentMapper.getFor(ActionComponent.class);
		this.hm = ComponentMapper.getFor(HealthComponent.class);
		this.pm = ComponentMapper.getFor(PositionComponent.class);
		this.mm = ComponentMapper.getFor(MovementComponent.class);
		this.tm = ComponentMapper.getFor(TypeComponent.class);
		this.world = world;

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
		MovementComponent movement 		= mm.get(entity);
		PositionComponent position 		= pm.get(entity);





	}

	//Attack another entity of type UNIT
	public void attack(Entity offender, Entity victim)
	{
		//Is unit entity
		if(!family.matches(offender) || !family.matches(victim)) return;

		HealthComponent health = victim.getComponent(HealthComponent.class);
		ActionComponent action = offender.getComponent(ActionComponent.class);
		health.health = health.health - action.damage;

		if(health.health <= 0) killUnit(victim);

	}

	/*Checks if Move is possible, performs Move if yes
	TODO
	Add collisiontest
	Add out of bounds(?). Out of bounds may simply not be chosen
*/
	public void move(Entity entity, Vector2 target_position)
	{
		if(!family.matches(entity)) return;

		PositionComponent position = entity.getComponent(PositionComponent.class);
		MovementComponent movement = entity.getComponent(MovementComponent.class);

		double distance_travel =
			Math.sqrt(Math.pow(target_position.x,2) + Math.pow(target_position.y,2))
			-
			Math.sqrt(Math.pow(position.position.x,2) + Math.pow(position.position.y,2));

		if(distance_travel <= movement.distance) position.position.set(target_position);


	}
	public void killUnit(Entity entity)
	{
		world.killUnit(entity);
	}

}
