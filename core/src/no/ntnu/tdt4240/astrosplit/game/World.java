package no.ntnu.tdt4240.astrosplit.game;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;




import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;
import no.ntnu.tdt4240.astrosplit.views.GameView;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class World {

	private PooledEngine engine;



	public World() {
		this.engine = GameView.getGameEngine();
	}

	//this method should create all units to be shown, (including background tiles?)
	public void create()
	{
		Entity firstEntity = createTestEntity(new Vector2(50,0));
		Entity secondEntity = createTestEntity(new Vector2(-50,0));
		firstEntity.getComponent(ActionComponent.class).attackList.add(secondEntity);

	}


	public Entity createTestEntity(Vector2 pos)
	{
		Entity entity = new Entity();

		PositionComponent pc 		= engine.createComponent(PositionComponent.class);
		TextureComponent tc 		= engine.createComponent(TextureComponent.class);
		ActionComponent ac 			= engine.createComponent(ActionComponent.class);
		TransformComponent tm 		= engine.createComponent(TransformComponent.class);
		ActorComponent am 			= engine.createComponent(ActorComponent.class);
		TypeComponent tp			= engine.createComponent(TypeComponent.class);
		HealthComponent hc 			= engine.createComponent(HealthComponent.class);
		MovementComponent mc 		= engine.createComponent(MovementComponent.class);
		ActionComponentAttack aca 	= engine.createComponent(ActionComponentAttack.class);

		tc.region = new TextureRegion(new Texture("units/marine_ranged.png"));
		tm.scale.set(0.1f,0.2f);
		pc.position = pos;

		entity.add(pc);
		entity.add(tc);
		entity.add(ac);
		entity.add(tm);
		entity.add(am);
		entity.add(tp);
		entity.add(hc);
		entity.add(mc);
		entity.add(aca);
		engine.addEntity(entity);

		return entity;
	}


	public void killUnit(Entity entity)
	{
		engine.removeEntity(entity);
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
