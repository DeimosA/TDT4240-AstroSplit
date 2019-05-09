package no.ntnu.tdt4240.astrosplit.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class HighlightedTileActor extends Actor {

	private Sprite sprite;

	public HighlightedTileActor()
	{
		sprite = new Sprite(new Texture("map/tile_01_top.png"));
		setTouchable(Touchable.enabled);
		addListener(new InputListener() {

			/*
				When clicked, move to tile
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println(String.format("x=%f\ty=%f",sprite.getX(),sprite.getY()));
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


}
