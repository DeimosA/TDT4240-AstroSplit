package no.ntnu.tdt4240.astrosplit.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.awt.Rectangle;

import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import com.badlogic.gdx.math.Vector2;

//Object has sprite and position,
//Handles on click events

public class UnitActor extends Actor {

	Sprite sprite;
	TransformComponent transformComponent;
	TextureComponent textureComponent;
	PositionComponent positionComponent;

	public UnitActor(TextureComponent texture, TransformComponent transform, PositionComponent pos)
	{

		this.textureComponent = texture;
		this.transformComponent = transform;
		this.positionComponent = pos;

		this.sprite = new Sprite(textureComponent.region.getTexture());

		//Every UnitActor is constructed with an eventlistener, TouchDown method.
		setTouchable(Touchable.enabled);
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("clicked");
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



}
