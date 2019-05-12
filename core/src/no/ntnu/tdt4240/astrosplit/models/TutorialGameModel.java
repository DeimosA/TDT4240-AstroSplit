package no.ntnu.tdt4240.astrosplit.models;


import com.badlogic.gdx.utils.Array;

/**
 * Game model for the tutorial
 */
public class TutorialGameModel extends GameModel {


	public TutorialGameModel() {
		super(GameType.TUTORIAL_GAME);
	}

	//Todo: fix
	@Override
	public TeamType[] getPlayerTypes() {
		return null;
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
	//Todo: Fix for tutorial
	@Override
	public Array<UnitModel> getUnits() {
		return null;
	}
}
