package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;


public class UnitInfoSubView extends SubView {

	// Element positions
	private Rectangle portraitPosition;
	private Rectangle actionInfoPosition;
	private Rectangle healthInfoPosition;
	private Rectangle movementInfoPosition;

	// Disposables
	private Texture unitPortrait;

	private Entity selectedEntity;

	private int actionDamage;
	private int actionRange;
	private int unitHealth;
	private int unitMaxHealth;
	private int movementRemaining;
	private int movementRange;


	/**
	 * Sub view for unit info
	 * @param bounds
	 */
	UnitInfoSubView(Rectangle bounds) {

		super(bounds);

		// TODO use assetmanager
		new Texture("Astro/GameModeSelection/buttonContinueWhite.png");
	}


	@Override
	void handleInput(Vector3 cursor) {
	}

	@Override
	void render(SpriteBatch sb, float deltaTime) {
	}

	@Override
	void dispose() {
	}

	@Override
	boolean goBack() {
		return true;
	}
}
