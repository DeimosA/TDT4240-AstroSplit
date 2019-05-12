package no.ntnu.tdt4240.astrosplit.models;


import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.astrosplit.enums.GameType;
import no.ntnu.tdt4240.astrosplit.enums.TeamType;

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
		return new TeamType[]{getPlayerTeam()};
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
		return new Array<UnitModel>();
	}
}
