package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;


public class UnitInfoSubView extends SubView {


	private MenuPresenter menuPresenter;

	/**
	 * Sub view for unit info
	 * @param bounds
	 * @param menuView
	 */

	// Element positions
	private Rectangle portraitPosition;
	private Rectangle actionInfoPosition;
	private Rectangle healthInfoPosition;
	private Rectangle movementInfoPosition;

	// Disposables
	private Texture unitPortrait;
	private int actionDamage;
	private int actionRange;
	private int unitHealth;
	private int unitMaxHealth;
	private int movementRemaining;
	private int movementRange;

	UnitInfoSubView(Rectangle bounds) {


		//super(bounds, menuView);

		new Texture("Astro/GameModeSelection/buttonContinueWhite.png");



	}



	@Override
	boolean goBack() {
		menuView.setSubView(new GameModeSubView(bounds, menuView, menuPresenter));
		return true;
	}
}
