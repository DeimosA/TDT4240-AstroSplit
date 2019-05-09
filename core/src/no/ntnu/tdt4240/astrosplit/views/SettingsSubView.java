package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;
import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class SettingsSubView extends SubView {


	private Configuration config;
	private MenuPresenter menuPresenter;


	/**
	 * Sub view for changing settings
	 * @param bounds
	 * @param menuView
	 */
	SettingsSubView(final Rectangle bounds, final MenuView menuView, MenuPresenter menuPresenter) {
		super(bounds, menuView);

		this.menuPresenter = menuPresenter;
		config = Configuration.getInstance();

		Array<MenuButton> combinedButtons = new Array<MenuButton>(false, 10, MenuButton.class);
		// Desktop only configs
		if (menuPresenter.isOnDesktopPlatform()) {
			combinedButtons.addAll(createDesktopButtons());
		}
		// Common configs
		combinedButtons.addAll(createCommonButtons());
		// Button list
		this.buttonList = new ButtonList(bounds, combinedButtons.shrink());
	}


	/**
	 * Settings that are only available on desktop
	 * @return
	 */
	private MenuButton[] createDesktopButtons() {
		return new MenuButton[] {
			// Fullscreen button
			new MenuButton(
				new Texture("Astro/Settings/buttonFullscreenOn.png"),
				new Texture("Astro/Settings/buttonFullscreenOff.png"),
				config.isFullscreen()) {
				@Override
				public void click() {
					// #1 - Fullscreen
					System.out.println("Clicked: Fullscreen");
					config.setFullScreen( ! this.getChecked());
					this.setChecked(config.isFullscreen());
				}
			},
		};
	}

	/**
	 * Settings buttons that are common for all platforms
	 * @return
	 */
	private MenuButton[] createCommonButtons() {
		return new MenuButton[] {
			// Sound effects setting
			new MenuButton(
				new Texture("Astro/Settings/buttonSoundOn.png"),
				new Texture("Astro/Settings/buttonSoundOff.png"),
				config.isSoundEffectsOn()) {
				@Override
				public void click() {
					// #3 - Sound effects
					System.out.println("Clicked: Sound");
					config.setSoundEffects( ! this.getChecked());
					this.setChecked(config.isSoundEffectsOn());
				}
			},
			// Music setting
			new MenuButton(
				new Texture("Astro/Settings/buttonMusicOn.png"),
				new Texture("Astro/Settings/buttonMusicOff.png"),
				config.isMusicOn()) {
				@Override
				public void click() {
					// #2 - Music
					System.out.println("Clicked: Music");
					config.setMusic( ! this.getChecked());
					this.setChecked(config.isMusicOn());
				}
			},
			// Back button
			new MenuButton(new Texture("Astro/GameModeSelection/buttonBack.png")) {
				@Override
				public void click() {
					// #4 - Back
					System.out.println("Chose: Back");
					// Main menu
					goBack();
				}
			},
		};
	}

	@Override
	boolean goBack() {
		config.save();
		menuView.setSubView(new MainMenuSubView(bounds, menuView, menuPresenter));
		return true;
	}
}
