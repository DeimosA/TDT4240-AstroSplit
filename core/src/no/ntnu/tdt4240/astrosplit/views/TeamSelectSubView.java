package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.models.LocalGameModel;
import no.ntnu.tdt4240.astrosplit.models.TeamType;
import no.ntnu.tdt4240.astrosplit.models.TutorialGameModel;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class TeamSelectSubView extends SubView {

	// Element positions
	private Rectangle rosterBoxPosition;
	private Rectangle playerNumberPosition;
	private Rectangle titlePosition;

	// Disposables
	private Texture rosterBoxTexture;
	private Texture playerNumberTexture;
	private Texture titleTexture;
	private Texture selectedTexture;
	private MenuButton[] buttons;
	private MenuButton confirmButton;

	private TeamType selectedTeam = null;
	private int selectedButtonIndex;
//	private boolean isLocalGame = false;
	private GameView.GameType gameType;
	private LocalGameModel localGameModel;


	TeamSelectSubView(Rectangle bounds, MenuView menuView, GameView.GameType gameType) {
		this(bounds, menuView, gameType, null);
	}

	TeamSelectSubView(Rectangle bounds, MenuView menuView, LocalGameModel gameModel) {
		this(bounds, menuView, GameView.GameType.LOCAL_GAME, gameModel);
	}

	/**
	 * Private constructor that should be used for all public
	 * @param bounds			The bounds of the subview
	 * @param menuView			The parent view
	 * @param gameType			Type of game
	 * @param localGameModel	The game model, if it has been created
	 */
	private TeamSelectSubView(Rectangle bounds,
							  MenuView menuView,
							  GameView.GameType gameType,
							  LocalGameModel localGameModel) {
		super(bounds, menuView);

		this.gameType = gameType;
		this.localGameModel = localGameModel;

		this.buttons = createButtons();

		// Calculate positions for 3 rows
		float rowHeight = bounds.height / 4;
		float rowCenter = rowHeight / 2;
		float xCenter = bounds.width / 2;

		// Calculate columns for 3 team buttons
		float colWidth = buttons[0].getTexture().getWidth() + 20;
		float colCenter = colWidth / 2f;
		float xFirstTeam = xCenter - 1.5f * colWidth;
		float xCenterFirstTeam = xFirstTeam + colCenter;
		float yCenter = bounds.height / 2;

		// Roster box background
		float rosterBoxBottomMargin = 10;
		float rosterBoxPadding = 20;
		rosterBoxPosition = new Rectangle(
			xFirstTeam - rosterBoxPadding,
			rosterBoxBottomMargin,
			(xCenter - xFirstTeam + rosterBoxPadding) * 2,
			bounds.height - rosterBoxBottomMargin
		);
		rosterBoxTexture = new Texture("Astro/TeamSelect/rosterBox.png");

		// Position of title
		titleTexture = new Texture("Astro/TeamSelect/titleTeamSelect.png");
		titlePosition = new Rectangle(
			xFirstTeam,
			bounds.height - titleTexture.getHeight() - 2 * rosterBoxPadding - 46,
			titleTexture.getWidth(), titleTexture.getHeight()
		);

		// Player number indicator
		if (gameType == GameView.GameType.LOCAL_GAME) {
			if (localGameModel == null) {
				// Local game and player 1 select
				playerNumberTexture = new Texture("Astro/TeamSelect/headingPlayer1.png");
			} else {
				// Local game and player 2 select
				playerNumberTexture = new Texture("Astro/TeamSelect/headingPlayer2.png");
			}
			// Player number indicator position
			playerNumberPosition = new Rectangle(
				titlePosition.x,
				bounds.height - playerNumberTexture.getHeight() - rosterBoxPadding,
				playerNumberTexture.getWidth(), playerNumberTexture.getHeight()
			);
		}

		// Team button positions
		for (int i = 0; i < 3; i++) {
			buttons[i].setCenterPosition(
				xCenterFirstTeam + i * colWidth,
				yCenter - 20
			);
		}

		// Back button
		buttons[3].setPosition(
			xFirstTeam,
			rosterBoxBottomMargin + rosterBoxPadding
		);

		// Confirm button
		confirmButton = buttons[4];
		confirmButton.setPosition(
			xFirstTeam + (3f * colWidth) - confirmButton.getTexture().getWidth(),
			rosterBoxBottomMargin + rosterBoxPadding
		);
		confirmButton.setDisabledTexture(new Texture("Astro/TeamSelect/buttonConfirmGray.png"));
		confirmButton.setEnabled(false);

		// Overlay texture for team selection
		selectedTexture = new Texture("Astro/TeamSelect/frameSelected.png");
	}


	/**
	 * Team selection buttons
	 * @return MenuButton array
	 */
	private MenuButton[] createButtons() {
		return new MenuButton[]{
			new MenuButton(new Texture("Astro/TeamSelect/teamGrays.png")) {
				@Override
				public void click() {
					// #1 - Team: Grays
					System.out.println("Chose: Grays");
					selectedButtonIndex = 0;
					selectTeam(TeamType.TEAM_GRAYS);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/teamMarines.png")) {
				@Override
				public void click() {
					// #2 - Team: Marines
					System.out.println("Chose: Marines");
					selectedButtonIndex = 1;
					selectTeam(TeamType.TEAM_MARINES);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/teamSectoids.png")) {
				@Override
				public void click() {
					// #3 - Team: Sectoids
					System.out.println("Chose: Sectoids");
					selectedButtonIndex = 2;
					selectTeam(TeamType.TEAM_SECTOIDS);
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/buttonBack.png")) {
				@Override
				public void click() {
					// #4 - Back
					System.out.println("Chose: Back");
					goBack();
				}
			},
			new MenuButton(new Texture("Astro/TeamSelect/buttonConfirmGolden.png")) {
				@Override
				public void click() {
					// #5 - Confirm
					if (selectedTeam != null) {
						System.out.println("Chose: Confirm");
						if (gameType == GameView.GameType.LOCAL_GAME && localGameModel == null) {
							// Player 1 have selected their team
							LocalGameModel gameModel = new LocalGameModel();
							gameModel.startNewGame();
							gameModel.setPlayer1(selectedTeam);
							// So let player 2 select
							menuView.setSubView(new TeamSelectSubView(bounds, menuView, gameModel));

						} else if (gameType == GameView.GameType.LOCAL_GAME) {
							// Player 2 have selected their team
							localGameModel.setPlayer2(selectedTeam);
							// So start the game
							ViewStateManager.getInstance().setScreen(
								new GameView(GameView.GameType.LOCAL_GAME, localGameModel));

						} else {
							// Other game types
							if (gameType == GameView.GameType.TUTORIAL_GAME) {
								TutorialGameModel gameModel = new TutorialGameModel();
								gameModel.setPlayerTeam(selectedTeam);
								ViewStateManager.getInstance().setScreen(new GameView(gameType, gameModel));
							}
						}
					}
				}
			}
		};
	}

	/**
	 * Set the selected team
	 * @param team The selected team
	 */
	private void selectTeam(TeamType team) {
		this.selectedTeam = team;
		this.confirmButton.setEnabled(true);
	}

	@Override
	void handleInput(Vector3 cursor) {
		// Confine to bounding box
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
		// Draw roster box
		sb.draw(
			rosterBoxTexture,
			bounds.x + rosterBoxPosition.x,
			bounds.y + rosterBoxPosition.y,
			rosterBoxPosition.width,
			rosterBoxPosition.height
		);

		// Draw player number
		if (playerNumberTexture != null) {
			sb.draw(
				playerNumberTexture,
				bounds.x + playerNumberPosition.x,
				bounds.y + playerNumberPosition.y,
				playerNumberPosition.width,
				playerNumberPosition.height
			);
		}

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
		if (selectedTeam != null) {
			sb.draw(
				selectedTexture,
				bounds.x + buttons[selectedButtonIndex].getBounds().x,
				bounds.y + buttons[selectedButtonIndex].getBounds().y,
				buttons[selectedButtonIndex].getBounds().width,
				buttons[selectedButtonIndex].getBounds().height
			);
		}
	}

	@Override
	void dispose() {
		titleTexture.dispose();
		selectedTexture.dispose();
		rosterBoxTexture.dispose();
		if (playerNumberTexture != null) playerNumberTexture.dispose();

		for (MenuButton button : buttons) {
			button.dispose();
		}
	}

	@Override
	boolean goBack() {
		menuView.setSubView(new GameModeSubView(bounds, menuView));
		return true;
	}
}
