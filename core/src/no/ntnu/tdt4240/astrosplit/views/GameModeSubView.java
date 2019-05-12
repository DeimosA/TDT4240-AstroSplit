package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.tdt4240.astrosplit.enums.GameType;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.models.LocalGameModel;
import no.ntnu.tdt4240.astrosplit.models.TutorialGameModel;
import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;
import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class GameModeSubView extends SubView {


	private MenuPresenter menuPresenter;


	/**
	 * Sub view for selecting game mode
	 * @param bounds
	 * @param menuView
	 */
	GameModeSubView(final Rectangle bounds, final MenuView menuView, final MenuPresenter menuPresenter) {
		super(bounds, menuView);

		this.menuPresenter = menuPresenter;

		/* Continue button, white texture if save exists */
		MenuButton continueButton = new MenuButton(
			new Texture("Astro/GameModeSelection/buttonContinueWhite.png"),
			new Texture("Astro/GameModeSelection/buttonContinueGray.png"),
			1f) {
			@Override
			public void click() {
				// #1 - Continue
				System.out.println("Clicked: Continue");
				LocalGameModel gameModel = new LocalGameModel();
				ViewStateManager.getInstance().setScreen(new GameView(gameModel));
			}
		};
		// Enable continue button if ongoing game exists
		if (LocalGameModel.hasOngoingGame()) {
			continueButton.setEnabled(true);
		} else {
			continueButton.setEnabled(false);
		}

		/* Game mode selection */
		this.buttonList = new ButtonList(bounds,
			new MenuButton[] {
				// Continue saved game
				continueButton,
				// Local vs game
				new MenuButton(new Texture("Astro/GameModeSelection/buttonLocal.png")) {
					@Override
					public void click() {
						// #3 - Local
						System.out.println("Chose: Local");
						menuView.setSubView(new TeamSelectSubView(
							bounds, menuView, menuPresenter, GameType.LOCAL_GAME));
					}
				},
				// Tutorial game
				new MenuButton(new Texture("Astro/GameModeSelection/buttonTutorial.png")) {
					@Override
					public void click() {
						// #4 - Tutorial
						System.out.println("Chose: Tutorial");

						TutorialGameModel gameModel = new TutorialGameModel();
			//			gameModel.setPlayerTeam(TeamType.TEAM_MARINES);
						ViewStateManager.getInstance().setScreen(new GameView(gameModel));

					}
				},
				// Go back
				new MenuButton(new Texture("Astro/GameModeSelection/buttonBack.png")) {
					@Override
					public void click() {
						// #6 - Back
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
		menuView.setSubView(new MainMenuSubView(bounds, menuView, menuPresenter));
		return true;
	}
}
