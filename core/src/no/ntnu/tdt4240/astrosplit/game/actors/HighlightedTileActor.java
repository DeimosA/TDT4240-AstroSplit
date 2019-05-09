package no.ntnu.tdt4240.astrosplit.game.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.math.Vector2;

public class HighlightedTileActor extends Actor {

	private Sprite sprite;
	private UnitActor actor;

	public HighlightedTileActor(final UnitActor actor)
	{
		sprite = new Sprite(new Texture("map/tile_01_top.png"));
		this.actor = actor;
		setTouchable(Touchable.enabled);
		addListener(new InputListener() {

			/*
				When clicked, move to tile
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				move(new Vector2(sprite.getX(),sprite.getY()));
				return true;
			}
		});

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

	public void setPosition(float x, float y)
	{
		sprite.setPosition(x,y);
		setBounds(
			sprite.getBoundingRectangle().getX(),
			sprite.getBoundingRectangle().getY(),
			sprite.getWidth(),
			sprite.getHeight());

		//tileSprite.setPosition(x,y);
	}

	private void move(Vector2 pos)
	{
		pos.set(new Vector2(pos.x-144,pos.y-144));
		actor.move(pos);
	}


}
