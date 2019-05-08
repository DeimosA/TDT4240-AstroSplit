package no.ntnu.tdt4240.astrosplit.presenters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.models.InteractionModel;
import no.ntnu.tdt4240.astrosplit.views.GameView;

public class InteractionPresenter {
	private InteractionModel interaction;
	public InteractionPresenter(InteractionModel interaction) {
		this.interaction = interaction;
	}

	//Used for selecting a new entity, also updates the range indicator in the game view
	public void updateInteraction(Entity selected, Class intent, Vector2 position) {
		System.out.println("Selected " + selected);
		interaction.setSelected(selected);
		interaction.setIntent(intent);
		interaction.setSelectedPosition(position);
		GameView.updateRange(0, null);
	}

	//Used for changing the intent
	public void updateIntent(Class intent) {
		interaction.setIntent(intent);
		GameView.updateRange(interaction.getRange(intent), interaction.getSelectedPosition());
	}
}
