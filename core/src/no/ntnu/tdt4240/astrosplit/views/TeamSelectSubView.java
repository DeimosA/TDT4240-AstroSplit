package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class TeamSelectSubView extends SubView {

	private Texture titleTexture;
	private Rectangle titlePosition;

	private MenuButton[] buttons;
	private MenuButton confirmButton;
	private Texture selectedTexture;

	private int selectedTeam = -1;


	TeamSelectSubView(final Rectangle bounds, final MenuView menuView) {
		super(bounds, menuView);

		/* Team selection buttons */
		this.buttons = new MenuButton[]{
			new MenuButton(new Texture("Astro/TeamSelect/teamGrays.png")) {
				@Override
				public void click() {
					// #1 - Team Grays
					System.out.println("Chose: Grays");
					selectTeam(0);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/teamMarines.png")) {
				@Override
				public void click() {
					// #2 - Team: Marines
					System.out.println("Chose: Marines");
					selectTeam(1);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/teamSectoids.png")) {
				@Override
				public void click() {
					// #3 - Team: Sectoids
					System.out.println("Chose: Sectoids");
					selectTeam(2);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/buttonBack.png")) {
				@Override
				public void click() {
					// #4 - Back
					System.out.println("Chose: Back");
					menuView.setSubView(new GameModeSubView(bounds, menuView));
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/buttonConfirmGolden.png")) {
				@Override
				public void click() {
					// #5 - Confirm
					if (selectedTeam > -1) {
						System.out.println("Chose: Confirm");
						ViewStateManager.getInstance().setScreen(new GameView());
					}
				}
			}
		};

		// Calculate positions for 3 rows
		float rowHeight = bounds.height / 3;
		float rowCenter = rowHeight / 2;
		float xCenter = bounds.width / 2;

		// Calculate columns for 3 team buttons
		float colWidth = buttons[0].getTexture().getWidth() + 20;
		float xCenterFirstTeam = xCenter - colWidth + 10;
		float yCenter = bounds.height / 2;

		// Position of title
		titleTexture = new Texture("Astro/TeamSelect/titleTeamSelect.png");
		titlePosition = new Rectangle(
			xCenter - titleTexture.getWidth() / 2f,
			bounds.height - rowCenter - titleTexture.getHeight() / 2f,
			titleTexture.getWidth(), titleTexture.getHeight()
		);

		// Team button positions
		for (int i = 0; i < 3; i++) {
			buttons[i].setCenterPosition(
				xCenterFirstTeam + i * colWidth,
				yCenter
			);
		}

		// Back button
		buttons[3].setCenterPosition(
			xCenterFirstTeam - (colWidth / 2f) + buttons[3].getTexture().getWidth() / 2f,
			rowCenter
		);

		// Confirm button
		confirmButton = buttons[4];
		confirmButton.setCenterPosition(
			xCenterFirstTeam + (2.5f * colWidth) - confirmButton.getTexture().getWidth() / 2f,
			rowCenter
		);
		confirmButton.setDisabledTexture(new Texture("Astro/TeamSelect/buttonConfirmGray.png"));
		confirmButton.setEnabled(false);

		// Overlay texture for team selection
		selectedTexture = new Texture("Astro/TeamSelect/frameSelected.png");
	}


	private void selectTeam(int team) {
		this.selectedTeam = team;
		this.confirmButton.setEnabled(true);
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

		// Draw title
		sb.draw(
			titleTexture,
			bounds.x + titlePosition.x,
			bounds.y + titlePosition.y,
			titlePosition.width,
			titlePosition.height
		);

		// Draw buttons
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

		// Draw selection overlay
		if (selectedTeam > -1) {
			sb.draw(
				selectedTexture,
				bounds.x + buttons[selectedTeam].getBounds().x,
				bounds.y + buttons[selectedTeam].getBounds().y,
				buttons[selectedTeam].getBounds().width,
				buttons[selectedTeam].getBounds().height
			);
		}
	}

	@Override
	void dispose() {
		titleTexture.dispose();
		selectedTexture.dispose();
		for (MenuButton button : buttons) {
			button.dispose();
		}
	}

}
