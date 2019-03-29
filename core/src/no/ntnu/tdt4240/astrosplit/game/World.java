package no.ntnu.tdt4240.astrosplit.game;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;




import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;
import no.ntnu.tdt4240.astrosplit.models.Map;
import no.ntnu.tdt4240.astrosplit.views.GameView;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class World {

	private PooledEngine engine;


	public World() {
		Entity worldEntity = new Entity();
		this.engine = GameView.getGameEngine();
		//this.engine.addEntity(worldEntity);
	}

	//this method should create all units to be shown, (including background tiles?)
	public void create()
	{
		createTestEntity(new Vector2(640,360));

	}


	/*Creates basic unit Entity, and ADDS to the engine
*/
	public Entity createUnit(Vector2 position_spawn, String type_unit)
	{
		Entity entity = engine.createEntity();

		ActionComponent action 				= engine.createComponent(ActionComponent.class);
		HealthComponent health				= engine.createComponent(HealthComponent.class);
		MovementComponent movement			= engine.createComponent(MovementComponent.class);
		PositionComponent position			= engine.createComponent(PositionComponent.class);
		TextureComponent texture 			= engine.createComponent(TextureComponent.class);
		TypeComponent type					= engine.createComponent(TypeComponent.class);
		TransformComponent transform 		= engine.createComponent(TransformComponent.class);

		//should specifytexture
		texture.region = new TextureRegion(new Texture("ground.png"));
		//Set scale
		transform.scale.set(0.2f,0.2f);

		//allows for null values
		if(position_spawn != null) position.position.set(position_spawn);
		if(type_unit != null) type.type = type_unit;

		entity.add(action);
		entity.add(health);
		entity.add(movement);
		entity.add(position);
		entity.add(texture);
		entity.add(type);
		entity.add(transform);

		engine.addEntity(entity);

		return entity;
	}

	public Entity createTestEntity(Vector2 pos)
	{
		Entity entity = new Entity();

		PositionComponent pc 		= engine.createComponent(PositionComponent.class);
		TextureComponent tc 		= engine.createComponent(TextureComponent.class);
		ActionComponent ac 			= engine.createComponent(ActionComponent.class);
		TransformComponent tm 		= engine.createComponent(TransformComponent.class);

		tc.region = new TextureRegion(new Texture("ground.png"));
		tm.scale.set(0.2f,0.2f);
		pc.position = pos;
		System.out.println(pos);

		entity.add(pc);
		entity.add(tc);
		entity.add(ac);
		entity.add(tm);
		engine.addEntity(entity);

		return entity;
	}


	public void killUnit(Entity entity)
	{

		Vector2 position = new Vector2(entity.getComponent(PositionComponent.class).position);

		engine.removeEntity(entity);

		createTerrain(position);


	}


	//Creates terrain at specified position
	public Entity createTerrain(Vector2 position)
	{
		Entity entity = engine.createEntity();

		TextureComponent texture 				= engine.createComponent(TextureComponent.class);
		PositionComponent position_component 	= engine.createComponent(PositionComponent.class);

		position_component.position.set(position);
		//Should specify what texture
		//Should specify scale


		entity.add(position_component);
		entity.add(texture);

		engine.addEntity(entity);

		return entity;

	}


}
