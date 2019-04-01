package no.ntnu.tdt4240.astrosplit.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;

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
		setTouchable(Touchable.enabled);
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("clicked");
				return true;
			}
		});

	}

	public void setPosition(float x, float y)
	{
		sprite.setPosition(x,y);
		setBounds(
			positionComponent.position.x,
			positionComponent.position.y,
			sprite.getWidth()*transformComponent.scale.x,
			sprite.getHeight()*transformComponent.scale.y);
		sprite.setScale(transformComponent.scale.x, transformComponent.scale.y);

		System.out.println(sprite.getBoundingRectangle());
	}

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

}
