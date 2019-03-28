package no.ntnu.tdt4240.astrosplit.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import jdk.nashorn.internal.ir.annotations.Immutable;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.models.Configuration;

public class RenderingSystem extends IteratingSystem{

	static final float renderWidth = Configuration.getInstance().getViewPortRenderWidth();
	static final float renderHeight = Configuration.getInstance().viewPortRenderHeight;



	//private OrthographicCamera camera;
	private SpriteBatch batch;
	private Comparator<Entity> comparator;
	private Array<Entity> renderQueue;
	private Family family;

	private ComponentMapper<TextureComponent> textureMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<PositionComponent> positionMapper;


	public RenderingSystem(SpriteBatch batch)
	{
		super(Family.all(TransformComponent.class, TextureComponent.class, PositionComponent.class).get());

		textureMapper = ComponentMapper.getFor(TextureComponent.class);
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		positionMapper = ComponentMapper.getFor(PositionComponent.class);

		renderQueue = new Array<Entity>();

		comparator = new Comparator<Entity>()
		{
			@Override
			public int compare(Entity A, Entity B)
			{
				return (int) Math.signum(transformMapper.get(B).height - transformMapper.get(A).height);
			}
		};

		this.batch = batch;

	//	this.camera = new OrthographicCamera(renderWidth, renderHeight);
	//	camera.position.set(renderWidth/2, renderHeight/2, 0);
		System.out.println("RenderWidth: " + renderWidth + "\tRenderHeight: " + renderHeight);

	}


	@Override
	public void update(float delta)
	{
		super.update(delta);

		renderQueue.sort(comparator);

		//camera.update();
		//batch.setProjectionMatrix(camera.combined);
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


			float width = tex.region.getRegionWidth()*t.scale.x;
			float height = tex.region.getRegionHeight()*t.scale.y;
			float originX = width/2;
			float originY = height/2;

			batch.draw(tex.region.getTexture(),
				p.position.x - originX, p.position.y - originY,
				originX,originY,
				width,height,
				t.scale.x,t.scale.y);

		}

		batch.end();
		renderQueue.clear();

	}


	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}
}
