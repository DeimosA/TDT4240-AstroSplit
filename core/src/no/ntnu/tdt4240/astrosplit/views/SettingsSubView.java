package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class SettingsSubView extends SubView {
	// Make buttons reachable inside of click()
	private MenuButton btnFullscreen;
	private MenuButton btnMusic;
	private MenuButton btnSound;

	// Texture-boxes checked or not
	private boolean fullscreenOn = false;
	private boolean musicOn; //false
	private boolean soundOn; //false

	/**
	 * Sub view for selecting game mode
	 * @param bounds
	 * @param menuView
	 */
	SettingsSubView(final Rectangle bounds, final MenuView menuView) {
		super(bounds, menuView);

		// Determine button box checked based on previously set preferences
		if(Configuration.getInstance().isFullScreen()){
			fullscreenOn = true;
		}
		//TODO
//		if(Configuration.getInstance().isMusic()){
			musicOn = true;
//		}
		//TODO
//		if(Configuration.getInstance().isSound()){
			soundOn = true;
//		}


		/* Fullscreen button (boolean boxChecked, texture)  */
		final MenuButton fullscreenButton = new MenuButton(
			fullscreenOn,
			new Texture("Astro/Settings/buttonFullscreenOn.png")
		) {
			@Override
			public void click() {
				// #1 - Fullscreen
				System.out.println("Clicked: Fullscreen");
				clicked(btnFullscreen);
				Configuration.getInstance().setFullScreen();
			}
		};
		fullscreenButton.setDisabledTexture(new Texture("Astro/Settings/buttonFullscreenOff.png"));

		/* Make fullscreenButton reachable inside of click() */
		btnFullscreen = fullscreenButton;


		/* Music volume button (boolean boxChecked, texture)*/
		MenuButton musicButton = new MenuButton(
			musicOn,
			new Texture("Astro/Settings/buttonMusicOn.png")
		) {
			@Override
			public void click() {
				// #2 - Music
				System.out.println("Clicked: Music");
				clicked(btnMusic);
			}
		};
		musicButton.setDisabledTexture(new Texture("Astro/Settings/buttonMusicOff.png"));

		// Make musicButton reachable inside of click()
		btnMusic = musicButton;


		/* Sound effects volume button (boolean boxChecked, texture) */
		MenuButton soundButton = new MenuButton(
			soundOn,
			new Texture("Astro/Settings/buttonSoundOn.png")
		) {
			@Override
			public void click() {
				// #3 - Sound effects
				System.out.println("Clicked: Sound");

				// Change texture
				clicked(btnSound);
			}
		};
		soundButton.setDisabledTexture(new Texture("Astro/Settings/buttonSoundOff.png"));

		// Make soundbutton reachable inside of click()
		btnSound = soundButton;


		/* Settings-buttons */
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

	// If clicked, change box checked value and texture
	private void clicked(MenuButton button){
		if(button.getChecked()){
			button.setChecked(false);
		} else {
			button.setChecked(true);
		}
	}

}
