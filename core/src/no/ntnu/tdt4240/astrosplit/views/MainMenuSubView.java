package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class MainMenuSubView implements SubView {

//	private MenuPresenter menuPresenter;

	private ButtonList buttonList;


	MainMenuSubView(Rectangle bounds, int menuSubType) {

		if(menuSubType == 1){
			mainMenuButtons(bounds);
		} else if(menuSubType == 2){
			teamSelectButtons(bounds);
		}
	}

	public void mainMenuButtons(Rectangle bounds){
		// Create buttons
		buttonList = new ButtonList(1, bounds,
			new MenuButton[] {
				/* Main menu */
				new MenuButton(new Texture("Astro/buttonStart.png")) {
					@Override
					public void click() {
						// Start game
//						ViewStateManager.getInstance().setScreen(new GameView());
//						ViewStateManager.getInstance().setScreen(new MenuRosterView());
						ViewStateManager.getInstance().setScreen(new MenuView(2));
					}
				},
				new MenuButton(new Texture("Astro/buttonSettings.png")) {
					@Override
					public void click() {
						// Settings
						System.out.println("Chose: Settings");
					}
				},
				new MenuButton(new Texture("Astro/buttonQuit.png")) {
					@Override
					public void click() {
						// Quit
						Gdx.app.exit();
					}
				}
			}
		);
	}

	public void teamSelectButtons(Rectangle bounds){
		buttonList = new ButtonList(2, bounds,
			new MenuButton[] {
				new MenuButton(new Texture("Astro/TeamSelect/titleTeamSelect.png")) {
					@Override
					public void click() {
						// #0 - Title
						System.out.println("Clicked: Title");
					}
				},
				new MenuButton(new Texture("Astro/TeamSelect/teamGrays.png")) {
					@Override
					public void click() {
						// #1 - Team Grays
						System.out.println("Chose: Grays");
						buttonList.teamSelected(0);
					}
				},
				new MenuButton(new Texture("Astro/TeamSelect/teamMarines.png")) {
					@Override
					public void click() {
						// #2 - Team: Marines
						System.out.println("Chose: Marines");
						buttonList.teamSelected(1);
					}
				},
				new MenuButton(new Texture("Astro/TeamSelect/teamSectoids.png")) {
					@Override
					public void click() {
						// #3 - Team: Sectoids
						System.out.println("Chose: Sectoids");
						buttonList.teamSelected(2);
					}
				},
				new MenuButton(new Texture("Astro/TeamSelect/buttonBack.png")) {
					@Override
					public void click() {
						// #4 - Back
						System.out.println("Chose: Back");
						// Main menu
						ViewStateManager.getInstance().setScreen(new MenuView(1));
					}
				},
				new MenuButton(new Texture("Astro/TeamSelect/buttonConfirmGray.png")) {
					@Override
					public void click() {
						// #5 -Confirm
						if (buttonList.getSelected()) {
							System.out.println("Chose: Confirm");
							// Main menu (test)
							ViewStateManager.getInstance().setScreen(new MenuView(1));
						}
					}
				},

				// Feedback texture
				new MenuButton(new Texture("Astro/TeamSelect/buttonConfirmGolden.png")) {
					// #6 -Golden confirm button on selection
				},
				new MenuButton(new Texture("Astro/TeamSelect/frameSelected.png")) {
					// #7 - Golden frame on selection
				}
			}
		);
	}

	public void handleInput(Vector3 cursor) {
		buttonList.handleInput(cursor);
	}

	public void render(SpriteBatch sb, float deltaTime) {
		buttonList.render(sb, deltaTime);
	}

	public void dispose() {
		buttonList.dispose();
	}
}
