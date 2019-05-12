package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;

public class ActionComponentAttack implements Component, ActionInterface {
	public int damage = 50;
	public int range = 2;

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
