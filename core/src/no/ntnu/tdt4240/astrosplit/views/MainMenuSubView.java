package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;
import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class MainMenuSubView extends SubView {

	private MenuPresenter menuPresenter;


	/**
	 * Subview for the main menu
	 * @param bounds	The bounds of the subview
	 * @param menuView	The parent view
	 */
	MainMenuSubView(final Rectangle bounds, final MenuView menuView, final MenuPresenter menuPresenter) {
		super(bounds, menuView);

		this.menuPresenter = menuPresenter;

		// Create buttons
		this.buttonList = new ButtonList(bounds,
			new MenuButton[] {
				/* Main menu */
				new MenuButton(new Texture("Astro/buttonStart.png")) {
					@Override
					public void click() {
						// Start game
						System.out.println("Chose: Start");
						menuView.setSubView(new GameModeSubView(bounds, menuView, menuPresenter));
					}
				},
				new MenuButton(new Texture("Astro/buttonSettings.png")) {
					@Override
					public void click() {
						// Settings
						System.out.println("Chose: Settings");
						menuView.setSubView(new SettingsSubView(bounds, menuView, menuPresenter));
					}
				},
				new MenuButton(new Texture("Astro/buttonQuit.png")) {
					@Override
					public void click() {
						// Quit
						goBack();
					}
				}
			}
		);
	}

	@Override
	boolean goBack() {
		// At first menu, so quit
		Gdx.app.exit();
		return true;
	}

}
