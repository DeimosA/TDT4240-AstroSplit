package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class LocalGameModel extends GameModel {


	private static final String saveGameName = "LocalGameState";
	private Preferences prefStore;

	// Game state to be saved in pref store
	private static final String ongoingGame = "ongoingGame";

	private TeamType player1;
	private TeamType player2;

	public LocalGameModel() {
		super(GameType.LOCAL_GAME);
		// Make sure saved gamestate is loaded if it exists
		load();
	}

	/**
	 * Set the team for player 1
	 * @param team
	 */
	public void setPlayer1(TeamType team) {
		this.player1 = team;
	}

	/**
	 * Set the team for player 2
	 * @param team
	 */
	public void setPlayer2(TeamType team) {
		this.player2 = team;
	}

	/**
	 * Clear any existing game state and start a new local game
	 */
	public void startNewGame() {
		prefStore.clear();
		prefStore.putBoolean(ongoingGame, true);
	}
	public void endGame() {
		prefStore.clear();
	}

	@Override
	public void load() {
		// Loads an existing game state or creates a new
		prefStore = Gdx.app.getPreferences(saveGameName);
	}

	@Override
	public void save() {
		prefStore.flush();
	}

	/**
	 * Static function to check if a saved game exists
	 * @return
	 */
	public static boolean hasOngoingGame() {
		Preferences prefs = Gdx.app.getPreferences(saveGameName);
		return prefs.contains(ongoingGame);
	}
}
