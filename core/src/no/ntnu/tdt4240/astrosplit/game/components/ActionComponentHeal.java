package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;

public class ActionComponentHeal implements Component, ActionInterface {
	public int heal = 25;
	public int range = 3;

	public boolean actionEnabled = true;


	@Override
	public boolean isActionAllowed() {
		return actionEnabled;
	}

	@Override
	public void resetAction() {
		actionEnabled = true;
	}
}
