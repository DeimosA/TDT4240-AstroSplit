package no.ntnu.tdt4240.astrosplit.game.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;


import no.ntnu.tdt4240.astrosplit.game.UI;
import no.ntnu.tdt4240.astrosplit.game.World;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.presenters.InteractionPresenter;


//Object has sprite and position,
//Handles on click events

public class UnitActor extends Actor {

	private Sprite sprite;
	private TransformComponent transformComponent;
	private TextureComponent textureComponent;
	private PositionComponent positionComponent;
	private MovementComponent movementComponent = null;
	Entity entity = null;


	public UnitActor(TextureComponent texture, TransformComponent transform, PositionComponent pos, final Entity entity)
	{

		this.textureComponent = entity.getComponent(TextureComponent.class);
		this.transformComponent = entity.getComponent(TransformComponent.class);
		this.positionComponent = entity.getComponent(PositionComponent.class);
		this.movementComponent = entity.getComponent(MovementComponent.class);
		this.entity = entity;

		this.sprite = new Sprite(textureComponent.region.getTexture());

		//Every UnitActor is constructed with an eventlistener, TouchDown method.
		setTouchable(Touchable.enabled);
		addListener(new InputListener() {

			/*
				Does at the moment move the actor 10px up at click
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				UI.getInteractionPresenter().updateInteraction(entity, null, positionComponent.position);
				/*
					TODO
					-Show some UI to choose action
					-Choose action
					-Move to specific tile on grid
					-Get next input after touched actor

					Alternatively:
					-Set this unit as chosen unit
						-Move logic to somewhere else
				 */
				return true;
			}
		});


	}

	/*
		Updates position of Sprite and bound
	 */
	public void setPosition(float x, float y)
	{
		sprite.setPosition(x,y);
		setBounds(
			sprite.getBoundingRectangle().getX(),
			sprite.getBoundingRectangle().getY(),
			sprite.getWidth()*transformComponent.scale.x,
			sprite.getHeight()*transformComponent.scale.y);
		sprite.setScale(transformComponent.scale.x, transformComponent.scale.y);
	}
	/*
		Update actor, calls to setPosition to apply these changes to view
	 */
	public void updateActor(TextureComponent tex, TransformComponent trans, PositionComponent pos)
	{
		this.textureComponent = tex;
		this.transformComponent = trans;
		this.positionComponent = pos;
		setPosition(positionComponent.position.x, positionComponent.position.y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		sprite.draw(batch);
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
	}



	public void setTouchable(boolean touchable)
	{
		if(touchable)
			setTouchable(Touchable.enabled);
		else
			{
				setTouchable(Touchable.disabled);
			}
	}

	public Vector2 getPosition()
	{
		return positionComponent.position;
	}



}
