package no.ntnu.tdt4240.astrosplit.models;

public abstract class GameModel implements Model {

	TeamType playerTeam;

	/**
	 * Set the team for the player
	 * @param playerTeam
	 */
	public void setPlayerTeam(TeamType playerTeam) {
		this.playerTeam = playerTeam;
	}

	@Override
	public void load() {
		// Do nothing by default
	}

	@Override
	public void save() {
		// Do nothing by default
	}
}
