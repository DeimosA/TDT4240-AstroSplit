package no.ntnu.tdt4240.astrosplit.game.components;

/**
 * To generalise actions and check if they are allowed or not
 */
public interface ActionInterface {

	boolean isActionAllowed();
	void resetAction();

}
