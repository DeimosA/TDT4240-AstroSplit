package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActorComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;

/**
 * Model keeps track of the entity that currently selected
 * Model also tracks what the 'intent' is, intent is a term for attack, movement or ability
 * Tracks the position of the selected entity
 * */
public class InteractionModel {


	private Entity selected;
	private Class intent;
	private Vector2 selectedPosition = null;


	public InteractionModel() {
		selected = null;
		intent = null;
	}


	public void setSelected(Entity selected) {
		if (this.selected == selected) return;

		if (this.selected != null) {
			// Unselect old selected
			this.selected.getComponent(ActorComponent.class).actor.select(false);
		}
		this.selected = selected;
	}

	public void setIntent(Class intent) {
		if (selected != null) {
			this.intent = intent;
			selected.getComponent(ActorComponent.class).actor.setActionIntent(intent);
			System.out.println("Intent: " + intent + " Set");
		} else {
			this.intent = null;
		}
	}

	public Class getIntent() {
		return intent;
	}

	public void setSelectedPosition(Vector2 selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public Vector2 getSelectedPosition() {
		return selectedPosition;
	}

	//Returns the range that the component has stated for the intent. Should never return -1
	public int getRange(Class intent) {
		if (intent == ActionComponent.class) {   //Not supposed to use range?
			return 0;
		} else if (intent == ActionComponentAttack.class) {
			return selected.getComponent(ActionComponentAttack.class).range;
		} else if (intent == MovementComponent.class) {
			return (int) selected.getComponent(MovementComponent.class).distance;
		} return -1;

	}
}
