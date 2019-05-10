package no.ntnu.tdt4240.astrosplit.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import no.ntnu.tdt4240.astrosplit.game.actors.UnitActor;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;

public class RenderingSystem extends IteratingSystem{

	private final Stage stage;

	//private OrthographicCamera camera;
	private SpriteBatch batch;
	private Comparator<Entity> comparator;
	private Array<Entity> renderQueue;
	private Family family;

	private ComponentMapper<TextureComponent> textureMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<ActorComponent> actorMapper;


	public RenderingSystem(SpriteBatch batch, Stage stage)
	{
		super(Family.all(TransformComponent.class, TextureComponent.class, PositionComponent.class, ActorComponent.class).get());

		textureMapper = ComponentMapper.getFor(TextureComponent.class);
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		actorMapper = ComponentMapper.getFor(ActorComponent.class);

		renderQueue = new Array<Entity>();


		/*
			Orders by z-index (height)
		 */
		comparator = new Comparator<Entity>()
		{
			@Override
			public int compare(Entity A, Entity B)
			{
				return (int) Math.signum(transformMapper.get(B).height - transformMapper.get(A).height);
			}
		};

		this.batch = batch;
		this.stage = stage;
	}

	/*
		Called on every tick
	 */
	@Override
	public void update(float delta)
	{
		super.update(delta);

		renderQueue.sort(comparator);

		batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
		batch.begin();

		for(Entity entity : renderQueue)
		{

			TextureComponent tex = textureMapper.get(entity);

			if(tex.region == null)
			{
				continue;
			}

			TransformComponent t = transformMapper.get(entity);
			PositionComponent p = positionMapper.get(entity);

			ActorComponent actor = actorMapper.get(entity);
			if(actor.actor == null)
			{
				actor.actor = new UnitActor(entity);
				stage.addActor(actor.actor);
			}
			else
				{
					actor.actor.updateActor(tex,t,p);
				}
			//stage.act


		}

		stage.draw();

		batch.end();
		renderQueue.clear();

	}

	/*
		Called on every entity, every tick
		Seems to activate before update()
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}
}
