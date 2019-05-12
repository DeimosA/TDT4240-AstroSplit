package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



import no.ntnu.tdt4240.astrosplit.game.GameWorld;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentHeal;
import no.ntnu.tdt4240.astrosplit.game.components.ActionInterface;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.Map;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.presenters.InteractionPresenter;
import no.ntnu.tdt4240.astrosplit.utils.Assets;
import no.ntnu.tdt4240.astrosplit.utils.AudioManager;
import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


public class GameView implements Screen {

	// Disposables
	private SpriteBatch spriteBatch;
	private Stage stage;
	private Map map;
	private Texture playerNumberTex;
	private Texture actionsBgTex;
	private Texture unitBgTex;
	private Texture actionSelectTex;
	private ButtonList actionButtons;
	private MenuButton endTurnButton;
	private MenuButton nextButton;
	private AssetManager assetManager;
	private UnitInfoSubView unitInfoSubView;

	// Some metrics
	private int renderHeight;
	private int renderWidth;
	private Rectangle mapBounds; // Map bounds in relation to render resolution
	private Rectangle actionSelectBounds;

	// Rendering
	private OrthographicCamera camera;
	private Viewport viewport;
	private Vector3 cursorPos = new Vector3();
	private boolean drawActionBox = false;

	// Game engine and related
	private PooledEngine engine;
	private InteractionPresenter interactionPresenter;
	private GameModel gameModel;
	private Entity selectedEntity = null;

	// Tutorial
	private int level = 0;
	private BitmapFont font;



	/**
	 * Constructs a view of a game
	 * @param gameModel
	 */
	GameView(GameModel gameModel) {
		this(gameModel, new AssetManager());
	}

	/**
	 * Constructs a view of a game
	 * @param gameModel
	 * @param assetManager
	 */
	GameView(GameModel gameModel, AssetManager assetManager) {
		this.gameModel = gameModel;
		this.assetManager = assetManager;
		this.interactionPresenter = InteractionPresenter.getInstance();
		interactionPresenter.setGameModel(gameModel);
		interactionPresenter.setGameView(this);

		// Setup rendering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		spriteBatch = new SpriteBatch();

		/* Load assets */
		Assets.loadHudAssets(assetManager);
		assetManager.finishLoading();

		/* UI viewport and camera */
		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, renderWidth, renderHeight);
		viewport = new FitViewport(renderWidth, renderHeight, camera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		/* Stage setup */
		map = new Map();
		// Map metrics in raw pixels
		int mapWidth = map.getMapWidthInPixels();
		int mapHeight = map.getMapHeightInPixels();
		// Map metrics in render pixels
		float mapAspectRatio = mapWidth / (float)mapHeight;
		mapBounds = new Rectangle();
		mapBounds.width = renderHeight * mapAspectRatio;
		mapBounds.x = (renderWidth - mapBounds.width) / 2f;
		mapBounds.y = 0;
		mapBounds.height = renderHeight;
		// Cam and viewport for map and stage
		OrthographicCamera stageCamera = new OrthographicCamera();
		stageCamera.setToOrtho(false, mapWidth, mapHeight);
		map.setCamera(stageCamera);
		Viewport stageViewport = new FitViewport(mapWidth, mapHeight, stageCamera);
		stage = new Stage(stageViewport);
		Gdx.input.setInputProcessor(stage);

		/* Engine and stuff */
		engine = new PooledEngine();
		GameWorld gameWorld = new GameWorld(engine, stage, assetManager, gameModel);
		gameWorld.create();
		interactionPresenter.setGameEngine(engine);

		// GameType specifics
		Assets.loadHudPlayerIndicators(assetManager);
		assetManager.finishLoading();

		font = new BitmapFont();
		font.getData().setScale(2f);

		switch (gameModel.getGameType()) {
			case TUTORIAL_GAME:
				Assets.loadTutorialAssets(assetManager);
				playerNumberTex = assetManager.get(Assets.hud_Player1_red, Texture.class);

				nextButton = createNextButton();
				nextButton.setCenterPosition(
					mapBounds.x + mapBounds.width + (renderWidth - mapBounds.width - mapBounds.x)/2f,75
				);

				break;

			case LOCAL_GAME:
				gameModel.save();

				if (gameModel.getPlayerTurn() == 1) {
					playerNumberTex = assetManager.get(Assets.hud_Player1_red, Texture.class);
				} else if (gameModel.getPlayerTurn() == 2) {
					playerNumberTex = assetManager.get(Assets.hud_Player2_blue, Texture.class);
				}
				// End turn button
				endTurnButton = createEndTurnButton();
				endTurnButton.setCenterPosition(
					mapBounds.x + mapBounds.width + (renderWidth - mapBounds.width - mapBounds.x) / 2f,
					75
				);
				break;
		}

		/* In-game UI */
		actionsBgTex = assetManager.get(Assets.hud_bg_actions, Texture.class);
		unitBgTex = assetManager.get(Assets.hud_bg_unitInfo, Texture.class);
		float actionButtonsScale = 3f;
		float actionButtonsWidth = 50f * actionButtonsScale;
		Rectangle actionBounds = new Rectangle(
			(mapBounds.x - actionButtonsWidth) / 2f,
			renderHeight * 0.4515625f,
			actionButtonsWidth, actionButtonsScale * 38 * 3
		);
		actionBounds.y = renderHeight * 0.4515625f - actionBounds.height / 2f;
		MenuButton[] actionButtonList = createActionButtons(actionButtonsScale);
		actionButtons = new ButtonList(actionBounds, actionButtonList);
		// Action selection visualization
		actionSelectTex = assetManager.get(Assets.hud_selectionBox, Texture.class);
		actionSelectBounds = new Rectangle(
			0,
			-100,
			actionSelectTex.getWidth() * actionButtonsScale,
			actionSelectTex.getHeight() * actionButtonsScale
		);
		actionSelectBounds.x = actionBounds.x + actionBounds.width / 2f - actionSelectBounds.width / 2f;


		unitInfoSubView = new UnitInfoSubView(new Rectangle(
			mapBounds.x + mapBounds.width,
			0,
			renderWidth - mapBounds.width - mapBounds.x,
			renderHeight
		), assetManager);
    
		/* Music */
		playMusicGame();
	}


	/* --- Public methods --- */

	/**
	 * To be run when the turn ended
	 * @param nextPlayerNumber
	 */
	public void turnEnded(int nextPlayerNumber) {
		selectedEntity = null;
		setActionSelectPos(null);

		int playerWonMaybe = interactionPresenter.checkWinCondition();
		if (playerWonMaybe > 0) {
			// Someone won
			gameModel.endGame();
			ViewStateManager.getInstance().setScreen(new GameOverView(playerWonMaybe));
			return;
		}

		if (nextPlayerNumber == 1) {
			playerNumberTex = assetManager.get(Assets.hud_Player1_red, Texture.class);
		} else if (nextPlayerNumber == 2) {
			playerNumberTex = assetManager.get(Assets.hud_Player2_blue, Texture.class);
		}
	}

	/**
	 * To notify view of selection change
	 * @param selectedUnit
	 */
	public void unitSelectionChanged(Entity selectedUnit) {
		selectedEntity = selectedUnit;
		unitInfoSubView.setSelectedUnit(selectedUnit);
		// Check if unit selected & current players unit
		if (selectedUnit != null &&
			interactionPresenter.getPlayerTurn() == selectedUnit.getComponent(PlayerComponent.class).id) {
			this.drawActionBox = true;
			int firstEnabledButton = -1;
			// Check if each button should be enabled
			for (int i = 0; i < actionButtons.getButtonCount(); i++) {
				MenuButton button = actionButtons.getButton(i);
				if (selectedUnit.getComponent(button.actionIntent) != null &&
					((ActionInterface)selectedUnit.getComponent(button.actionIntent)).isActionAllowed()
				) {
					button.setEnabled(true);
					if (firstEnabledButton < 0 && button.isEnabled()) {
						firstEnabledButton = i;
					}
				} else {
					button.setEnabled(false);
				}
			}
			// Set intent as the first enabled button
			if (firstEnabledButton > -1) {
				actionButtons.getButton(firstEnabledButton).click();
			} else {
				setActionSelectPos(null);
			}
		} else {
			this.drawActionBox = false;
			setActionSelectPos(null);
		}
	}

	public void unitIntentChanged(Class actionIntent) {
		setActionSelectPos(null);
	}

	public void disableIntent(Class actionIntent) {

	}

	/* --- Private methods --- */

	/**
	 * Creates button for ending turn
	 * @return
	 */
	private MenuButton createEndTurnButton() {
		return new MenuButton(assetManager.get(Assets.hud_button_endTurn, Texture.class)) {
			@Override
			public void click() {
				System.out.println("Clicked End Turn!");
				selectedEntity = null;
				setActionSelectPos(null);
				interactionPresenter.endTurn();
			}
		};
	}

	private MenuButton createNextButton(){
		return new MenuButton(assetManager.get(Assets.hud_tutorial_next, Texture.class))
		{
			//How deep into the tutorial we are

			@Override
			public void click()
			{
				System.out.println("NEXT");
				//selectedEntity = null;
				//setActionSelectPos(null);
				interactionPresenter.endTurn();
				level+=1;
			}
		};
	}

	private void drawTutorialUI(SpriteBatch spriteBatch)
	{
		switch (this.level)
		{
			case 0:
				Texture text = assetManager.get(Assets.hud_tutorial_text, Texture.class);

		}


	}

	/**
	 * Create buttons for available actions
	 * @param scale Scale of button icons
	 * @return
	 */
	private MenuButton[] createActionButtons(float scale) {
		return new MenuButton[] {
			new MenuButton( // Move action
				assetManager.get(Assets.hud_button_move, Texture.class),
				assetManager.get(Assets.hud_button_move_disabled, Texture.class),
				scale, MovementComponent.class) {
				@Override
				public void click() {
					System.out.println("Move action!");
					setActionSelectPos(this);
					interactionPresenter.updateIntent(this.actionIntent);
				}
			},
			new MenuButton( // Attack action
				assetManager.get(Assets.hud_button_sword, Texture.class),
				assetManager.get(Assets.hud_button_sword_disabled, Texture.class),
				scale, ActionComponentAttack.class) {
				@Override
				public void click() {
					System.out.println("Attack action!");
					setActionSelectPos(this);
					interactionPresenter.updateIntent(this.actionIntent);
				}
			},
			new MenuButton( // Heal action
				assetManager.get(Assets.hud_button_heal, Texture.class),
				assetManager.get(Assets.hud_button_heal_disabled, Texture.class),
				scale, ActionComponentHeal.class) {
				@Override
				public void click() {
					System.out.println("Heal action!");
					setActionSelectPos(this);
					interactionPresenter.updateIntent(this.actionIntent);
				}
			}
		};
	}

	/**
	 * Set position of the action select rectangle to a button
	 * @param button The selected button, or null if no selection
	 */
	private void setActionSelectPos(MenuButton button) {
		if (button != null) {
			float buttonCenterY = actionButtons.getBounds().y + button.getBounds().y + button.getBounds().height / 2f;
			actionSelectBounds.y = buttonCenterY - actionSelectBounds.height / 2f;
		} else {
			actionSelectBounds.y = -100;
		}
	}

	/**
	 * Play in-game music
	 */
	public void playMusicGame(){
		AudioManager.getInstance().PlayMusicGame();
	}

	/**
	 * Handle input
	 */
	private void handleInput() {
		/* Check pos touched */
		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
			camera.unproject(cursorPos);
			// Check if any buttons are pushed
			if (actionButtons.getBounds().contains(cursorPos.x, cursorPos.y) && selectedEntity != null) {
				actionButtons.handleInput(cursorPos);
			}
			if(gameModel.getGameType() == GameModel.GameType.LOCAL_GAME)
				endTurnButton.handleInput(cursorPos.x, cursorPos.y);
			if(gameModel.getGameType() == GameModel.GameType.TUTORIAL_GAME)
				nextButton.handleInput(cursorPos.x,cursorPos.y);
		}
	}

	/* --- Overridden methods --- */

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		handleInput();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.getViewport().apply();
		map.render();
		engine.update(delta);	//Will update the RenderingSystem, displaying game characters

		viewport.apply(false);
		spriteBatch.setProjectionMatrix(camera.combined);
		drawUI(delta);	//Overlay

	}

	/**
	 * Draws overlay UI
	 * @param delta
	 */
	private void drawUI(float delta) {
		spriteBatch.begin();

		// Backgrounds
		spriteBatch.draw(
			actionsBgTex,
			0, 0,
			mapBounds.x, renderHeight
		);
		spriteBatch.draw(
			unitBgTex,
			mapBounds.x + mapBounds.width, 0,
			renderWidth - (mapBounds.x + mapBounds.width), renderHeight
		);

		// Buttons
		if (drawActionBox && selectedEntity != null) {
			actionButtons.render(spriteBatch, delta);
		}
		if (actionSelectBounds.y > 0) spriteBatch.draw(
			actionSelectTex,
			actionSelectBounds.x, actionSelectBounds.y,
			actionSelectBounds.width, actionSelectBounds.height
		);
		// Player number
		if (playerNumberTex != null) {
			spriteBatch.draw(
				playerNumberTex,
				mapBounds.x + 15, renderHeight - playerNumberTex.getHeight() - 15,
				playerNumberTex.getWidth(), playerNumberTex.getHeight()
			);
		}
		// Selected unit info
		if (selectedEntity != null) unitInfoSubView.render(spriteBatch, delta);

		//Local EndTurn Button
		if(gameModel.getGameType() == GameModel.GameType.LOCAL_GAME)
		{
			spriteBatch.draw(
				endTurnButton.getTexture(),
				endTurnButton.getBounds().x, endTurnButton.getBounds().y,
				endTurnButton.getBounds().width, endTurnButton.getBounds().height
			);
		}

		//Tutorial Next Button
		if(gameModel.getGameType() == GameModel.GameType.TUTORIAL_GAME)
		{
			spriteBatch.draw(
				nextButton.getTexture(),
				nextButton.getBounds().x, nextButton.getBounds().y,
				nextButton.getBounds().width, nextButton.getBounds().height
			);

			Texture text = assetManager.get(Assets.hud_tutorial_text, Texture.class);
			Texture tex = assetManager.get(Assets.hud_tutorial_arrow, Texture.class);
			Sprite arrow = new Sprite(tex);
			arrow.flip(true,false);

			switch(level)
			{
				case 0:
					//Welcome and abilities
					spriteBatch.draw(text,350,120,580,500);
					font.draw(spriteBatch,
						"Welcome to the Tutorial!\n" +
							"Here we will show you the basic steps\n" +
							"to rule the Galaxy!\n" +
							"Select one of the alien Straw Men\n" +
							"and choose an Ability on the left.\n" +
							"You may move around, attack enemies\n" +
							"and heal allies.\n" +
							"Straws don't get tired.\n" +
							"Play as much as you like!\n" +
							"Press Next for more info.",
						380,550);
					break;
				case 1:
					spriteBatch.draw(arrow, 250,600,100,100);
					break;
				case 2:
					//Unit info
					spriteBatch.draw(text,350,120,580,500);
					font.draw(spriteBatch,
						"Good job!\n" +
							"The Unit Info to the right\n" +
							"is important in battle.\n" +
							"Tap units to see their status...\n" +
							"Attack your opponent or heal your ally!\n" +
							"Their scores are at your hands!\n" +
							"Use the Unit Info to your advantage!",
						380,550);
					break;
				case 3:
					arrow.flip(true,false);
					spriteBatch.draw(arrow, 900, 600,100,100);
					break;
				case 4:
					//End of tutorial
					spriteBatch.draw(text,350,200,580,400);
					font.draw(spriteBatch,
						"That's it!\n" +
							"Welcome to the Astro Split!\n" +
							"Fight for the Galaxy's limited resources.\n" +
							"Play as Marines, Sectoids, or Grays and\n" +
							"crush your friends in Multiplayer battle!\n" +
							"Remember that only you,\n" +
							"can make a difference...",
						380,550);
					break;
				case 5:
					//Go to Main Menu
					ViewStateManager.getInstance().setScreen(new MenuView());
					break;




			}

		}
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		// Calculate how the map viewport should be in relation to the UI viewport
		float mapAspect = mapBounds.width / mapBounds.height;
		int newMapHeight = Math.min((int)(width * renderHeight / (float)renderWidth), height);
		int newMapWidth = Math.min((int)(width * mapBounds.width / renderWidth), (int)(newMapHeight * mapAspect));
		int newMapX = (width - newMapWidth) / 2;
		int newMapY = (height - newMapHeight) / 2;
		// Update map viewport
		stage.getViewport().update(newMapWidth, newMapHeight, false);
		stage.getViewport().setScreenPosition(newMapX, newMapY);
		// update UI viewport
		viewport.update(width, height, false);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
//		shape.dispose();
		stage.dispose();
		map.dispose();
		if (playerNumberTex != null) playerNumberTex.dispose();
		actionsBgTex.dispose();
		unitBgTex.dispose();
		actionSelectTex.dispose();
		actionButtons.dispose();
		assetManager.dispose();
		unitInfoSubView.dispose();
	}
}
