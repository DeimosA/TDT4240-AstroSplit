package no.ntnu.tdt4240.astrosplit.game.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.astrosplit.game.abilities.Move;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.presenters.InteractionPresenter;


//Object has sprite and position,
//Handles on click events
public class UnitActor extends Actor {


	private Sprite sprite;
	private Entity entity;
	private TransformComponent transformComponent;
	private TextureComponent textureComponent;
	private PositionComponent positionComponent;
	private MovementComponent movementComponent = null;

	private boolean isSelected = false;
	private Class actionIntent;
	private boolean showMovementRange = false;
	private Array<HighlightedTileActor> tileList = new Array<HighlightedTileActor>();
	private int gridSize;


	public UnitActor(TextureComponent texture, TransformComponent transform, PositionComponent pos, final Entity entity)
	{
		this.textureComponent = entity.getComponent(TextureComponent.class);
		this.transformComponent = entity.getComponent(TransformComponent.class);
		this.positionComponent = entity.getComponent(PositionComponent.class);
		this.movementComponent = entity.getComponent(MovementComponent.class);
		this.entity = entity;
		this.gridSize = (int) Math.pow(movementComponent.distance,2);
		this.sprite = new Sprite(textureComponent.region.getTexture());

		//Every UnitActor is constructed with an eventlistener, TouchDown method.
		setTouchable(Touchable.enabled);
		addListener(inputListener);
	}

	private InputListener inputListener = new InputListener() {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			select(true);
			return true;
		}
	};

	/* --- Public methods --- */

	/**
	 * Set whether this unit is selected or not
	 * @param select
	 */
	public void select(boolean select)
	{
		isSelected = select;
		if (select) {
			// On select
			InteractionPresenter.getInstance().updateInteraction(entity, actionIntent, positionComponent.position);
		} else {
			// On unselect
			destroyMovementTiles();
		}
	}

	/**
	 * Set the intended action for this actor/entity
	 * @param intent Component class
	 */
	public void setActionIntent(Class intent) {
		this.actionIntent = intent;
		// Remove all intent visualisations first
		destroyMovementTiles();

		if (intent == MovementComponent.class) {
			showMovementRange = true;
		} else if (intent == ActionComponentAttack.class) {
			// TODO something attack related
		}
	}

	/**
	 * Updates position of Sprite and bound
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

	/**
	 * Update actor, calls to setPosition to apply these changes to view
	 */
	public void updateActor(TextureComponent tex, TransformComponent trans, PositionComponent pos)
	{
		this.textureComponent = tex;
		this.transformComponent = trans;
		this.positionComponent = pos;
		setPosition(positionComponent.position.x, positionComponent.position.y);
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

	public void move(Vector2 pos)
	{
		if(collisionCheck(pos))
			Move.move(entity,pos); // TODO use entity-component

		destroyMovementTiles();
		// TODO temporary thing
		InteractionPresenter.getInstance().disableIntent(MovementComponent.class);
	}

	/* --- Private methods --- */

	private void drawMovementTiles()
	{
		for(int posx = -(int)movementComponent.distance*32; posx <= movementComponent.distance*32; posx +=32)
		{
			for(int posy = -(int)movementComponent.distance*32; posy <= movementComponent.distance*32; posy +=32)
			{
				if(0 == posx && 0 == posy)
				{
					gridSize-=1;
					continue;
				}
				if(posx+144+positionComponent.position.x > 256 || posx+144+positionComponent.position.x < 32)
				{
					gridSize-=1;
					continue;
				}
				if(posy+144+positionComponent.position.y > 256 || posy+144+positionComponent.position.y < 32)
				{
					gridSize-=1;
					continue;
				}
				if(!collisionCheck(new Vector2(posx+positionComponent.position.x,posy+positionComponent.position.y)))
				{
					gridSize-=1;
					continue;
				}
				if(Math.abs(posx)/32 + Math.abs(posy)/32 > movementComponent.distance)
				{
					gridSize-=1;
					continue;
				}
				HighlightedTileActor tile = new HighlightedTileActor(this);
				this.getStage().addActor(tile);
				tile.setPosition(posx+144+positionComponent.position.x,posy+144+positionComponent.position.y);
				tileList.add(tile);
			}
		}
	}

	private void destroyMovementTiles() {
		showMovementRange = false;
		for(HighlightedTileActor tileActor: tileList)
		{
			tileActor.remove();

		}
		tileList.clear();
		gridSize = (int) Math.pow(movementComponent.distance,2);
	}

	private boolean collisionCheck(Vector2 pos)
	{
		for(Actor actor : this.getStage().getActors()){
			if(actor.getClass() == UnitActor.class)
			{
				if(pos.x+144 == actor.getX() && pos.y+144 == actor.getY())
					return false;
			}
		}
		return true;
	}

	/* --- Overridden methods --- */

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		sprite.draw(batch);
		// Draw intent if selected
		if (isSelected) {
			if(showMovementRange && tileList.size < gridSize) {
				drawMovementTiles();
			}
		}
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
	}
}
