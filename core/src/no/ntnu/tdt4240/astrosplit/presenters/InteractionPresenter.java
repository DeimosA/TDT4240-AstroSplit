package no.ntnu.tdt4240.astrosplit.presenters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.models.InteractionModel;
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


	/**
	 * Private constructor
	 * @param interactionModel
	 */
	private InteractionPresenter(InteractionModel interactionModel) {
		this.interactionModel = interactionModel;
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
		this.gameModel = gameModel;
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

	public void disableIntent(Class intent) {
		// TODO use proper method in gameview
		gameView.unitIntentChanged(intent);
	}

	/**
	 * Used for changing the action intent
	 * @param intent
	 */
	public void updateIntent(Class intent) {
		interactionModel.setIntent(intent);
//		GameView.updateRange(interactionModel.getRange(intent), interactionModel.getSelectedPosition());
	}
}
