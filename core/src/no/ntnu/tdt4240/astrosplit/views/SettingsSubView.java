package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class SettingsSubView extends SubView {


	/**
	 * Sub view for selecting game mode
	 * @param bounds
	 * @param menuView
	 */
	SettingsSubView(final Rectangle bounds, final MenuView menuView) {
		super(bounds, menuView);

		/* Fullscreen button, box texture checked if previously set */
		MenuButton fullscreenButton = new MenuButton(
			new Texture("Astro/Settings/buttonFullscreenOn.png")
		) {
			@Override
			public void click() {
				// #1 - Fullscreen
				System.out.println("Clicked: Fullscreen");
			}
		};
		fullscreenButton.setDisabledTexture(new Texture("Astro/Settings/buttonFullscreenOff.png"));
		// Fullscreen box checked if enabled
//		if (LocalGameModel.hasOngoingGame()) {
//			fullscreenButton.setEnabled(true);
//		} else {
//			fullscreenButton.setEnabled(false);
//		}


		/* Music volume button, box texture unchecked if turned off */
		MenuButton musicButton = new MenuButton(
			new Texture("Astro/Settings/buttonMusicOn.png")
		) {
			@Override
			public void click() {
				// #2 - Music
				System.out.println("Clicked: Fullscreen");
			}
		};
		musicButton.setDisabledTexture(new Texture("Astro/Settings/buttonMusicOff.png"));
		// Music box unchecked if turned off
//		if (LocalGameModel.hasOngoingGame()) {
//			musicButton.setEnabled(true);
//		} else {
//			musicButton.setEnabled(false);
//		}

		/* Sound effects volume button, box texture unchecked if turned off */
		MenuButton soundButton = new MenuButton(
			new Texture("Astro/Settings/buttonSoundOn.png")
		) {
			@Override
			public void click() {
				// #3 - Sound effects
				System.out.println("Clicked: Fullscreen");
			}
		};
		soundButton.setDisabledTexture(new Texture("Astro/Settings/buttonMusicOff.png"));
		// Sound box unchecked if turned off
//		if (LocalGameModel.hasOngoingGame()) {
//			soundButton.setEnabled(true);
//		} else {
//			soundButton.setEnabled(false);
//		}

		/* Settings */
		this.buttonList = new ButtonList(bounds,
			new MenuButton[] {
				fullscreenButton,
				musicButton,
				soundButton,
				new MenuButton(new Texture("Astro/GameModeSelection/buttonBack.png")) {
					@Override
					public void click() {
						// #4 - Back
						System.out.println("Chose: Back");
						// Main menu
						goBack();
					}
				},
			}
		);

	}

	@Override
	boolean goBack() {
		menuView.setSubView(new MainMenuSubView(bounds, menuView));
		return true;
	}

}
