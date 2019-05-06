package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;

class TeamSelectSubView extends SubView {

	private MenuButton[] buttons;

	// Row
	private float rowHeight;
	private float rowCenter;
	private float xCenter;

	// Column
	private float colWidth;
	private float colCenter;
	private float yCenter;

	private int selectedTeam = 0;


	TeamSelectSubView(final Rectangle bounds, final MenuView menuView) {
		super(bounds, menuView);

		// Calculate positions for buttons within bounds
		this.rowHeight = bounds.height / 3;
		this.rowCenter = rowHeight / 2;
		this.xCenter = bounds.width / 2;

		// Columns
		this.colWidth = bounds.width / 3;
		this.colCenter = colWidth / 2;
		this.yCenter = bounds.height / 2;


		/* Team selection */
		this.buttons = new MenuButton[]{
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
					selectTeam(1);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/teamMarines.png")) {
				@Override
				public void click() {
					// #2 - Team: Marines
					System.out.println("Chose: Marines");
					selectTeam(2);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/teamSectoids.png")) {
				@Override
				public void click() {
					// #3 - Team: Sectoids
					System.out.println("Chose: Sectoids");
					selectTeam(3);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/buttonBack.png")) {
				@Override
				public void click() {
					// #4 - Back
					System.out.println("Chose: Back");
					// Main menu
					menuView.setSubView(new GameModeSubView(bounds, menuView));
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/buttonConfirmGray.png")) {
				@Override
				public void click() {
					// #5 -Confirm
					if (selectedTeam > 0) {
						System.out.println("Chose: Confirm");
						ViewStateManager.getInstance().setScreen(new GameView());
					}
				}
			}
		};

		setButtonPositions();

		// Feedback texture
//		new MenuButton(new Texture("Astro/TeamSelect/buttonConfirmGolden.png")) {
//			// #6 -Golden confirm button on selection
//		},
//			new MenuButton(new Texture("Astro/TeamSelect/frameSelected.png")) {
//				// #7 - Golden frame on selection
//			}
	}


	private void setButtonPositions() {
		// Title
		buttons[0].setCenterPosition(
			xCenter,
			bounds.getHeight() - buttons[0].getTexture().getHeight()
		);

		// Team
		for (int i = 0; i < 3; i++) {
			buttons[i + 1].setCenterPosition(
				colCenter + i * colWidth,
				yCenter
			);
		}

		// Button: back
		buttons[4].setCenterPosition(
			buttons[4].getTexture().getWidth(),
			buttons[4].getTexture().getHeight()
		);

		// Button: confirm
		buttons[5].setCenterPosition(
			buttons[5].getTexture().getWidth() + bounds.width / 2,
			buttons[5].getBounds().height
		);

		/* Draw outside viewport */
		// Button: golden confirm
//		buttons[6].setCenterPosition(
//			renderWidth - buttons[6].getTexture().getWidth(),
//			renderHeight - buttons[6].getTexture().getHeight()
//		);
		//Golden frame
//		buttons[7].setCenterPosition(
//			renderWidth - buttons[7].getTexture().getWidth(),
//			renderHeight - buttons[7].getTexture().getHeight()
//		);
	} // menu 3

	private void selectTeam(int team) {
		this.selectedTeam = team;
		// TODO show selected team
	}

	@Override
	void handleInput(Vector3 cursor) {
		float x = cursor.x - bounds.x;
		float y = cursor.y - bounds.y;
		// Check each button
		for (MenuButton button : buttons) {
			if (button.handleInput(x, y)) {
				return;
			}
		}
	}

	@Override
	void render(SpriteBatch sb, float deltaTime) {
		for (MenuButton button : buttons) {
			Rectangle bb = button.getBounds(); // Button bounds

			sb.draw(
				button.getTexture(),
				bounds.x + bb.x,
				bounds.y + bb.y,
				bb.width,
				bb.height
			);
		}
	}

	@Override
	void dispose() {
		for (MenuButton button : buttons) {
			button.dispose();
		}
	}

}
