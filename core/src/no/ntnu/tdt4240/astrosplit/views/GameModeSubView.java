package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class GameModeSubView extends SubView {


	GameModeSubView(final Rectangle bounds, final MenuView menuView) {
		super(bounds, menuView);

		// Continue button, white texture if save exists
		MenuButton continueButton = new MenuButton(new Texture("Astro/GameModeSelection/buttonContinueWhite.png")) {
			@Override
			public void click() {
				// #1 - Continue
				System.out.println("Clicked: Continue");
				// TODO load game
			}
		};
		continueButton.setDisabledTexture(new Texture("Astro/GameModeSelection/buttonContinueGray.png"));
		continueButton.setEnabled(false); // TODO check first

		/* Game mode selection */
		this.buttonList = new ButtonList(bounds,
			new MenuButton[] {
				continueButton,
//					new MenuButton(new Texture("Astro/GameModeSelection/buttonOnline.png")) {
//						@Override
//						public void click() {
//							// #2 - Online
//							System.out.println("Chose: Online");
//							ViewStateManager.getInstance().setScreen(new MenuView(3));
//						}
//					},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonLocal.png")) {
					@Override
					public void click() {
						// #3 - Local
						System.out.println("Chose: Local");
						menuView.setSubView(new TeamSelectSubView(bounds, menuView));
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonTutorial.png")) {
					@Override
					public void click() {
						// #4 - Tutorial
						System.out.println("Chose: Tutorial");
						// TODO tut
					}
				},
				// Empty space
//				new MenuButton(new Texture("Astro/GameModeSelection/buttonEmpty.png")),
				new MenuButton(new Texture("Astro/GameModeSelection/buttonBack.png")) {
					@Override
					public void click() {
						// #6 - Back
						System.out.println("Chose: Back");
						// Main menu
						menuView.setSubView(new MainMenuSubView(bounds, menuView));
					}
				},
			}
		);
	}

}
