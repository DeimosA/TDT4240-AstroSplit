package no.ntnu.tdt4240.astrosplit.models;


/**
 * Game model for the tutorial
 */
public class TutorialGameModel extends GameModel {


	public TutorialGameModel() {
		super(GameType.TUTORIAL_GAME);
	}


	@Override
	public int getPlayerTurn() {
		return 1;
	}

	@Override
	public void endTurn() {

	}

	@Override
	public void endGame() {

	}
}
