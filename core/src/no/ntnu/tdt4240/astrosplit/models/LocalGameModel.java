package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class LocalGameModel implements Model {


	private static final String saveGameName = "LocalGameState";
	private Preferences prefStore;

	private TeamType player1;
	private TeamType player2;

	public LocalGameModel() {
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
	 * Check if a saved game exists
	 * @return
	 */
	public static boolean hasOngoingGame() {
		Preferences prefs = Gdx.app.getPreferences(saveGameName);
		return prefs.contains("ongoingGame");
	}
}
