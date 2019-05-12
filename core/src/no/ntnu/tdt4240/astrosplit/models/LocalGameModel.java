package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class LocalGameModel extends GameModel implements Json.Serializable {


	private static final String saveGameName = "LocalGameState";
	private Preferences prefStore;
	private Json json = new Json();

	// Game state to be saved in pref store
	private static final String ongoingGame = "ongoingGame";
	private static final String playerTurn = "playerTurn";
	private static final String unitsModel = "unitsModel";

	// Saves P1 and P2 units.
	private Array<UnitModel> units = new Array();

	private static TeamType player1;
	private static TeamType player2;


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
		prefStore.putInteger(playerTurn, 1);
		prefStore.flush();
	}

	/**
	 * Static function to check if a saved game exists
	 * @return
	 */
	public static boolean hasOngoingGame() { // Replace prefs with load and prefStore?
		Preferences prefs = Gdx.app.getPreferences(saveGameName);
		return prefs.contains(ongoingGame);
	}

	// Overrides the saved model with the units param
	private void saveUnits(Array<UnitModel> units) {
		prefStore.putString(unitsModel, json.toJson(units, Array.class));
		save();
	}

	// Returns the current saved units model
	public Array<UnitModel> getUnits() {
		return json.fromJson(Array.class, prefStore.getString(unitsModel));
	}

	@Override
	public void write(Json json) {

	}

	@Override
	public void read(Json json, JsonValue jsonData) {

	}

	@Override
	public int getPlayerTurn() {
		Preferences prefs = Gdx.app.getPreferences(saveGameName);
		return prefs.getInteger(playerTurn);
	}

	public static TeamType[] getPlayerTypes() {
		TeamType[] playerTypes = {player1, player2};
		return playerTypes;
	}

	@Override
	public void endTurn() {
		prefStore.putInteger(playerTurn, (getPlayerTurn() == 1) ? 2 : 1);
		prefStore.flush();
	}

	@Override
	public void endGame() {
		prefStore.clear();
		prefStore.flush();
	}

	@Override
	public void load() {
		// Loads an existing game state or creates a new
		prefStore = Gdx.app.getPreferences(saveGameName);
	}

	@Override
	public void save() {
		if(!prefStore.contains(ongoingGame)) {
			prefStore.putBoolean(ongoingGame, true);
		}
		prefStore.flush();
	}
}
