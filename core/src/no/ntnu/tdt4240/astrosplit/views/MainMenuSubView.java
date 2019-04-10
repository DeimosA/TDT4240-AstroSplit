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
			gameModeButtons(bounds);

		} else if(menuSubType == 3){
			teamSelectButtons(bounds);
		}
	}

	private void mainMenuButtons(Rectangle bounds){
		// Create buttons
		buttonList = new ButtonList(1, bounds,
			new MenuButton[] {
				/* Main menu */
				new MenuButton(new Texture("Astro/buttonStart.png")) {
					@Override
					public void click() {
						// Start game
						System.out.println("Chose: Start");
//						ViewStateManager.getInstance().setScreen(new GameView());
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

	/* Game mode selection */
	private void gameModeButtons(Rectangle bounds){
		buttonList = new ButtonList(2, bounds,
			new MenuButton[] {
				// Continue button, white texture if save exists
				new MenuButton(new Texture("Astro/GameModeSelection/buttonContinueGray.png")) {
					@Override
					public void click() {
						// #1 - Continue
						System.out.println("Clicked: Continue");
						if(buttonList.getSave()){
							ViewStateManager.getInstance().setScreen(new MenuView(1));
						}
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonOnline.png")) {
					@Override
					public void click() {
						// #2 - Online
						System.out.println("Chose: Online");
						ViewStateManager.getInstance().setScreen(new MenuView(3));
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonLocal.png")) {
					@Override
					public void click() {
						// #3 - Local
						System.out.println("Chose: Local");
						ViewStateManager.getInstance().setScreen(new MenuView(3));
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonTutorial.png")) {
					@Override
					public void click() {
						// #4 - Tutorial
						System.out.println("Chose: Tutorial");

						//test
						buttonList.setSave();
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonEmpty.png")) {
					@Override
					public void click() {
						// #5 - Empty button
						System.out.println("Clicked: Emtpy button");
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonBack.png")) {
					@Override
					public void click() {
						// #6 - Back
						System.out.println("Chose: Back");
						// Main menu
						ViewStateManager.getInstance().setScreen(new MenuView(1));
					}
				},

				//White continue texture
				new MenuButton(new Texture("Astro/GameModeSelection/buttonContinueWhite.png")) {
					@Override
					public void click() {
						// #7 - Continue
						System.out.println("Chose: Continue");
					}
				}
			}
		);
	}

	/* Team selection */
	private void teamSelectButtons(Rectangle bounds){
		buttonList = new ButtonList(3, bounds,
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
						ViewStateManager.getInstance().setScreen(new MenuView(2));
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
