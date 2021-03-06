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


import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentHeal;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentTarget;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TextureComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TransformComponent;
import no.ntnu.tdt4240.astrosplit.presenters.InteractionPresenter;


/**
 * Helper class for LibGDX scene system
 * Handles unit selection events
 */
public class UnitActor extends Actor {


	private Sprite sprite;
	private Entity entity;
	private TransformComponent transformComponent;
	private TextureComponent textureComponent;
	private PositionComponent positionComponent;
	private MovementComponent movementComponent = null;
	private ActionComponentAttack attackComponent;
	private ActionComponentHeal healComponent;
	private PlayerComponent playerComponent;
	private ActionComponentTarget targetComponent;
	private ActionComponent actionComponent;

	private boolean isSelected = false;
	private boolean shouldUpdateInteraction;
	private Class actionIntent;
	private boolean showMovementRange = false;
	private boolean showAttackRange = false;
	private boolean showHealRange = false;
	private Array<HighlightedTileActor> tileList = new Array<HighlightedTileActor>();
	private int gridSizeAttack;
	private int gridSizeHeal;
	private int gridSizeMovement;


	public UnitActor(final Entity entity)
	{
		this.textureComponent = entity.getComponent(TextureComponent.class);
		this.transformComponent = entity.getComponent(TransformComponent.class);
		this.positionComponent = entity.getComponent(PositionComponent.class);
		this.movementComponent = entity.getComponent(MovementComponent.class);
		this.attackComponent = entity.getComponent(ActionComponentAttack.class);
		this.healComponent = entity.getComponent(ActionComponentHeal.class);
		this.playerComponent = entity.getComponent(PlayerComponent.class);
		this.entity = entity;
		this.sprite = new Sprite(textureComponent.region.getTexture());
		this.targetComponent = entity.getComponent(ActionComponentTarget.class);
		this.actionComponent = entity.getComponent(ActionComponent.class);

		//Every UnitActor is constructed with an eventlistener, TouchDown method.
		setTouchable(Touchable.enabled);
		addListener(inputListener);

		if (movementComponent != null) gridSizeMovement = (int) Math.pow(movementComponent.distance, 2);
		if (attackComponent != null) gridSizeAttack = (int) Math.pow(attackComponent.range, 2);
		if (healComponent != null) gridSizeHeal = (int) Math.pow(healComponent.range, 2);
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
		if (InteractionPresenter.getInstance().getPlayerTurn() == playerComponent.id) {
			isSelected = select;
		} else {
			isSelected = false;
			actionIntent = null;
		}
		if (select) {
			// On select
			updateInteraction();
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
		// Remove all intent visualisations first
		destroyMovementTiles();
		destroyAttackTiles();
		destroyHealTiles();

		if (isSelected) {
			this.actionIntent = intent;

			if (intent == MovementComponent.class) {
				showMovementRange = true;
			} else if (intent == ActionComponentAttack.class) {
				showAttackRange = true;
			} else if (intent == ActionComponentHeal.class){
				showHealRange = true;
			}
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

	/* --- Protected methods --- */
	public void move(Vector2 position)
	{
		if (isSelected && movementComponent.isActionAllowed()) {
			if(collisionCheck(position)) {
				if(entity.getComponent(MovementComponent.class) != null)
				{
					entity.getComponent(MovementComponent.class).position = position;
				}
			}
			destroyMovementTiles();
			movementComponent.remainingPoints -= 1;
			if ( ! movementComponent.isActionAllowed()) {
				actionIntent = null;
				shouldUpdateInteraction = true;
			}
		}
	}


	public void attack(Vector2 pos)
	{
		if (isSelected && actionComponent.remainingPoints > 0) {
			for(Actor actor : this.getStage().getActors())
			{
				if(actor.getClass() == UnitActor.class)
				{
					if(pos.equals(((UnitActor) actor).getPosition())) {
						entity.getComponent(ActionComponentTarget.class).target = (((UnitActor) actor).getEntity());
						actionComponent.remainingPoints -= 1;
						if (actionComponent.remainingPoints <= 0) {
							attackComponent.actionEnabled = false;
							actionIntent = null;
							shouldUpdateInteraction = true;
						}
					}
				}
			}
			destroyAttackTiles();
		}
	}

	public void heal(Vector2 pos)
	{
		if (isSelected && actionComponent.remainingPoints > 0) {
			for(Actor actor : this.getStage().getActors())
			{
				if(actor.getClass() == UnitActor.class)
				{
					if(pos.equals(((UnitActor) actor).getPosition()))
						entity.getComponent(ActionComponentTarget.class).target = (((UnitActor) actor).getEntity());
						actionComponent.remainingPoints -= 1;
						if (actionComponent.remainingPoints <= 0) {
							healComponent.actionEnabled = false;
							actionIntent = null;
							shouldUpdateInteraction = true;
						}
				}
			}
			destroyHealTiles();
		}
	}

	public Entity getEntity()
	{
		return this.entity;
	}
	public int getPlayer()
	{
		return playerComponent.id;
	}

	/* --- Private methods --- */

	/**
	 * Update UI with interaction
	 */
	private void updateInteraction() {
		InteractionPresenter.getInstance().updateInteraction(entity, actionIntent, positionComponent.position);
	}

	private void drawMovementTiles()
	{
		this.gridSizeMovement = (int) Math.pow(movementComponent.distance,2);

		for(int posx = -(int)movementComponent.distance*32; posx <= movementComponent.distance*32; posx +=32)
		{
			for(int posy = -(int)movementComponent.distance*32; posy <= movementComponent.distance*32; posy +=32)
			{
				if(0 == posx && 0 == posy)
				{
					gridSizeMovement-=1;
					continue;
				}
				if(posx+144+positionComponent.position.x > 256 || posx+144+positionComponent.position.x < 32)
				{
					gridSizeMovement-=1;
					continue;
				}
				if(posy+144+positionComponent.position.y > 256 || posy+144+positionComponent.position.y < 32)
				{
					gridSizeMovement-=1;
					continue;
				}
				if(!collisionCheck(new Vector2(posx+positionComponent.position.x,posy+positionComponent.position.y)))
				{
					gridSizeMovement-=1;
					continue;
				}
				if(Math.abs(posx)+ Math.abs(posy)> movementComponent.distance*32)
				{
					gridSizeMovement-=1;
					continue;
				}
				HighlightedTileActor tile = new HighlightedTileActor(this,'M');
				this.getStage().addActor(tile);
				tile.setPosition(posx+144+positionComponent.position.x,posy+144+positionComponent.position.y);
				tileList.add(tile);
			}
		}
	}
	private void drawAttackTiles()
	{
		for(int posx = -attackComponent.range*32; posx <= attackComponent.range*32; posx +=32)
		{
			for(int posy = -attackComponent.range*32; posy <= attackComponent.range*32; posy +=32)
			{
				if(0 == posx && 0 == posy)
				{
					gridSizeAttack-=1;
					continue;
				}
				if(posx+144+positionComponent.position.x > 256 || posx+144+positionComponent.position.x < 32)
				{
					gridSizeAttack-=1;
					continue;
				}
				if(posy+144+positionComponent.position.y > 256 || posy+144+positionComponent.position.y < 32)
				{
					gridSizeAttack-=1;
					continue;
				}
				if(!friendCheck(new Vector2(posx+positionComponent.position.x,posy+positionComponent.position.y)))
				{
					gridSizeAttack-=1;
					continue;
				}
				if(Math.abs(posx)+ Math.abs(posy)> attackComponent.range*32)
				{
					gridSizeAttack-=1;
					continue;
				}
				HighlightedTileActor tile = new HighlightedTileActor(this,'A');
				this.getStage().addActor(tile);
				tile.setPosition(posx+144+positionComponent.position.x,posy+144+positionComponent.position.y);
				tileList.add(tile);
			}
		}
	}

	private void drawHealTiles()
	{
		for(int posx = -healComponent.range*32; posx <= healComponent.range*32; posx +=32)
		{
			for(int posy = -healComponent.range*32; posy <= healComponent.range*32; posy +=32)
			{
				if(0 == posx && 0 == posy)
				{
					gridSizeHeal-=1;
					continue;
				}
				if(posx+144+positionComponent.position.x > 256 || posx+144+positionComponent.position.x < 32)
				{
					gridSizeHeal-=1;
					continue;
				}
				if(posy+144+positionComponent.position.y > 256 || posy+144+positionComponent.position.y < 32)
				{
					gridSizeHeal-=1;
					continue;
				}
				if(friendCheck(new Vector2(posx+positionComponent.position.x,posy+positionComponent.position.y)))
				{
					gridSizeHeal-=1;
					continue;
				}
				if(Math.abs(posx)+ Math.abs(posy)> healComponent.range*32)
				{
					gridSizeHeal-=1;
					continue;
				}
				HighlightedTileActor tile = new HighlightedTileActor(this,'H');
				this.getStage().addActor(tile);
				tile.setPosition(posx+144+positionComponent.position.x,posy+144+positionComponent.position.y);
				tileList.add(tile);
			}
		}
	}


	private void destroyMovementTiles()
	{
		if (movementComponent != null) {
			showMovementRange = false;
			for(HighlightedTileActor tileActor: tileList)
			{
				tileActor.remove();
			}
			tileList.clear();
			gridSizeMovement = (int) Math.pow(movementComponent.distance,2);
		}
	}

	private void destroyAttackTiles()
	{
		if(attackComponent != null){
			showAttackRange = false;
			for(HighlightedTileActor tileActor: tileList)
			{
				tileActor.remove();
			}
			tileList.clear();
			gridSizeAttack = (int) Math.pow(attackComponent.range,2);
		}
	}

	private void destroyHealTiles()
	{
		if(healComponent != null){
			showHealRange = false;
			for(HighlightedTileActor tileActor: tileList)
			{
				tileActor.remove();
			}
			tileList.clear();
			gridSizeHeal = (int) Math.pow(healComponent.range,2);
		}
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

	private boolean friendCheck(Vector2 pos)
	{
		for(Actor actor : this.getStage().getActors()){
			if(actor.getClass() == UnitActor.class)
			{
				int i = ((UnitActor) actor).getPlayer();
				if(i == this.getPlayer() && pos.x+144 == actor.getX() && pos.y+144 == actor.getY())
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
			if(showMovementRange && tileList.size < gridSizeMovement)
			{
				drawMovementTiles();
			}
			else if(showAttackRange && tileList.size < gridSizeAttack)
			{
				drawAttackTiles();
			}
			else if(showHealRange && tileList.size < gridSizeHeal)
			{
				drawHealTiles();
			}
		}
		if (shouldUpdateInteraction) {
			shouldUpdateInteraction = false;
			updateInteraction();
		}
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
	}

}
