package no.ntnu.tdt4240.astrosplit.presenters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.astrosplit.enums.GameType;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentHeal;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.models.InteractionModel;
import no.ntnu.tdt4240.astrosplit.models.LocalGameModel;
import no.ntnu.tdt4240.astrosplit.enums.TeamType;
import no.ntnu.tdt4240.astrosplit.models.UnitModel;
import no.ntnu.tdt4240.astrosplit.views.GameView;


/**
 * Connects UI and game engine.
 * We want to make sure there is only one of these, so singleton
 */
public class InteractionPresenter {


	private static InteractionPresenter INSTANCE = null;

	private InteractionModel interactionModel;
	private GameView gameView;
	private GameModel gameModel;
	private PooledEngine engine;
	private Family playerEntitiesFamily;


	/**
	 * Private constructor
	 * @param interactionModel
	 */
	private InteractionPresenter(InteractionModel interactionModel) {
		this.interactionModel = interactionModel;
		playerEntitiesFamily = Family.all(PlayerComponent.class).get();
	}

	/**
	 * Returns the singular instance
	 * @return
	 */
	public static InteractionPresenter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new InteractionPresenter(new InteractionModel());
		}
		return INSTANCE;
	}

	/* --- Public methods --- */

	/**
	 * Set the game view that this should interact with
	 * @param gameView
	 */
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	/**
	 * Set the type of game we are playing and its model
	 * @param gameModel
	 */
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

	public void setGameEngine(PooledEngine engine) {
		this.engine = engine;
	}

	/**
	 * Used for selecting a new entity, also updates the range indicator in the game view
	 * @param selected
	 * @param intent
	 * @param position
	 */
	public void updateInteraction(Entity selected, Class intent, Vector2 position) {
		System.out.println("Selected " + selected);
		interactionModel.setSelected(selected);
		interactionModel.setIntent(intent);
		interactionModel.setSelectedPosition(position);

		gameView.unitSelectionChanged(selected);
	}

	/**
	 * Runs when user ends turn
	 */
	public void endTurn() {
		// TODO end turn stuff (done?)
		// Update game state
		//Reset general action actions
		for (Entity entity : engine.getEntitiesFor(Family.all(ActionComponent.class).get())) {
			entity.getComponent(ActionComponent.class).resetAction();
			entity.getComponent(ActorComponent.class).actor.setActionIntent(null);
			entity.getComponent(ActorComponent.class).actor.select(false);
		}
		// reset attacks
		for (Entity entity : engine.getEntitiesFor(Family.all(ActionComponentAttack.class).get())) {
			entity.getComponent(ActionComponentAttack.class).resetAction();
		}
		// reset heal
		for (Entity entity : engine.getEntitiesFor(Family.all(ActionComponentHeal.class).get())) {
			entity.getComponent(ActionComponentHeal.class).resetAction();
		}
		// reset move
		for (Entity entity : engine.getEntitiesFor(Family.all(MovementComponent.class).get())) {
			entity.getComponent(MovementComponent.class).resetAction();
		}

		// Save game state
		if (gameModel.getGameType() == GameType.LOCAL_GAME) {
			saveUnits(engine);
		}
		// Switch player
		gameModel.endTurn();
		// Tell the view to update
		gameView.turnEnded(gameModel.getPlayerTurn());
	}

	public void saveUnits(PooledEngine engine) {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(playerEntitiesFamily);
		((LocalGameModel)gameModel).saveUnits(entities);
	}

	public Array<UnitModel> getUnits() {
		return gameModel.getUnits();
  	}

	public int getPlayerTurn() {
		return gameModel.getPlayerTurn();
	}

	public void disableIntent(Class intent) {
		gameView.disableIntent(intent);
	}

	public TeamType[] getPlayerTypes() {
		return gameModel.getPlayerTypes();
	}

	/**
	 * Used for changing the action intent
	 * @param intent
	 */
	public void updateIntent(Class intent) {
		interactionModel.setIntent(intent);
	}

	public int checkWinCondition() {
		boolean p1HasUnits = false;
		boolean p2HasUnits = false;
		for (UnitModel unit : gameModel.getUnits()) {

			if (unit.player == 1) {
				p1HasUnits = true;
			} else if (unit.player == 2) {
				p2HasUnits = true;
			}
		}
		if (p1HasUnits && p2HasUnits) {
			return -1;
		} else {
			return p1HasUnits ? 1 : 2;
		}
	}

	public void dispose() {

		INSTANCE = null;
		interactionModel = null;
		gameView = null;
		gameModel = null;
		engine = null;
		playerEntitiesFamily = null;

	}
}
