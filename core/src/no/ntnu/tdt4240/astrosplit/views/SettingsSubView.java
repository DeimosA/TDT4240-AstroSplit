package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Menu;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class SettingsSubView extends SubView {
	private MenuButton btnFullscreen;
	private MenuButton btnMusic;
	private MenuButton btnSound;

	private boolean fullscreenOn = false;
	private boolean musicOn;
	private boolean soundOn;

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

		/* Fullscreen button (boolean, texture)  */
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

		/* Fullscreen button, set variable to make it accessible inside of click() */
		btnFullscreen = fullscreenButton;

		// At load: If checked == true, change texture
//		checked(fullscreenButton);


		/* Music volume button, box texture unchecked if turned off */
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

		// Make musicButton reachable inside click()
		btnMusic = musicButton;

		/* Sound effects volume button, box texture unchecked if turned off */
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

		// Make soundbutton reachable inside click()
		btnSound = soundButton;

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

	void clicked(MenuButton button){
		if(button.getChecked()){
			button.setChecked(false);
		} else {
			button.setChecked(true);
		}
	}

}
