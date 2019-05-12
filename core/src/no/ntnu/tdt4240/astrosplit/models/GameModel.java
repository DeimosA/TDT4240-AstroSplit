package no.ntnu.tdt4240.astrosplit.models;


import com.badlogic.gdx.utils.Array;

public abstract class GameModel implements Model {

	public abstract TeamType[] getPlayerTypes();

	/**
	 * Valid types of game
	 */
	public enum GameType {
		TUTORIAL_GAME,
		LOCAL_GAME,
//		ONLINE_GAME,
	}

	TeamType playerTeam;
	GameType gameType;


	/**
	 * Game model must be initialised with a game type
	 * @param gameType
	 */
	GameModel(GameType gameType) {
		this.gameType = gameType;
	}

	/**
	 * Set the team for the player
	 * @param playerTeam
	 */
	public void setPlayerTeam(TeamType playerTeam) {
		this.playerTeam = playerTeam;
	}

	/**
	 * Get the type of game for this model
	 * @return
	 */
	public GameType getGameType() {
		return gameType;
	}

	/**
	 * Get the player number for the current turn
	 * @return
	 */
	public abstract int getPlayerTurn();

	/**
	 * End the current turn
	 */
	public abstract void endTurn();

	/**
	 * End the current game
	 */
	public abstract void endGame();

	/**
	 * Returns the units from the game model
	 */
	public abstract Array<UnitModel> getUnits();

	@Override
	public void load() {
		// Do nothing by default
	}

	@Override
	public void save() {
		// Do nothing by default
	}
}
