package no.ntnu.tdt4240.astrosplit.presenters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.models.InteractionModel;
import no.ntnu.tdt4240.astrosplit.models.LocalGameModel;
import no.ntnu.tdt4240.astrosplit.models.TeamType;
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
	private static GameModel gameModel;
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
	public void setGameWiew(GameView gameView) {
		this.gameView = gameView;
	}

	/**
	 * Set the type of game we are playing and its model
	 * @param gameModel
	 */
	public void setGameModel(GameModel gameModel) {
		this.gameModel = (LocalGameModel) gameModel;
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
//		GameView.updateRange(0, null);
	}

	/**
	 * Runs when user ends turn
	 */
	public void endTurn() {
		gameModel.endTurn();
		// TODO end turn stuff

		// Save game state
		if (gameModel.getGameType() == GameModel.GameType.LOCAL_GAME) {
			ImmutableArray<Entity> entities = engine.getEntitiesFor(playerEntitiesFamily);
			((LocalGameModel)gameModel).saveUnits(entities);
		}

		gameView.turnEnded(gameModel.getPlayerTurn());
	}

	public static Array<UnitModel> getUnits() {
		return gameModel.getUnits();
	}

	public void disableIntent(Class intent) {
		// TODO use proper method in gameview
		gameView.unitIntentChanged(intent);
	}

	public static TeamType[] getPlayerTypes() {
		return gameModel.getPlayerTypes();
	}

	/**
	 * Used for changing the action intent
	 * @param intent
	 */
	public void updateIntent(Class intent) {
		interactionModel.setIntent(intent);
	}
}
