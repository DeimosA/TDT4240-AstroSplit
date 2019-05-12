package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



import no.ntnu.tdt4240.astrosplit.game.GameWorld;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentHeal;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.Map;
import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.models.LocalGameModel;
import no.ntnu.tdt4240.astrosplit.presenters.InteractionPresenter;
import no.ntnu.tdt4240.astrosplit.utils.Assets;
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
	private AssetManager assetManager;

	// Some metrics
	private int renderHeight;
	private int renderWidth;
	private Rectangle mapBounds; // Map bounds in relation to render resolution
	private Rectangle actionSelectBounds;

	// Rendering
	private OrthographicCamera camera;
	private Viewport viewport;
	private Vector3 cursorPos = new Vector3();

	// Game engine and related
	private PooledEngine engine;
	private InteractionPresenter interactionPresenter;
	private GameModel gameModel;
	private Entity selectedEntity = null;


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
		interactionPresenter.setGameWiew(this);

		// Maybe do som game type specific stuff
		switch (gameModel.getGameType()) {
			case TUTORIAL_GAME:
				break;

			case LOCAL_GAME:
				Assets.loadHudPlayerIndicators(assetManager);
				assetManager.finishLoading();
				if (gameModel.getPlayerTurn() == 1) {
					playerNumberTex = assetManager.get(Assets.hud_Player1_red, Texture.class);
				} else if (gameModel.getPlayerTurn() == 2) {
					playerNumberTex = assetManager.get(Assets.hud_Player2_blue, Texture.class);
				}
				break;
		}
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
		GameWorld gameWorld = new GameWorld(engine, stage, assetManager);
		gameWorld.create();

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
		// End turn button
		endTurnButton = createEndTurnButton();
		endTurnButton.setCenterPosition(
			mapBounds.x + mapBounds.width + (renderWidth - mapBounds.width - mapBounds.x) / 2f,
			75
		);
	}


	/* --- Public methods --- */

	/**
	 * To be run when the turn ended
	 * @param nextPlayerNumber
	 */
	public void turnEnded(int nextPlayerNumber) {
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
		if (selectedUnit != null) {
			int firstEnabledButton = -1;
			for (int i = 0; i < actionButtons.getButtonCount(); i++) {
				MenuButton button = actionButtons.getButton(i);

				// TODO also check if unit has remaining actions!
				button.setEnabled(selectedUnit.getComponent(button.actionIntent) != null);
				if (firstEnabledButton < 0 && button.isEnabled()) {
					firstEnabledButton = i;
				}
			}
			if (firstEnabledButton > -1) {
				actionButtons.getButton(firstEnabledButton).click();
			} else {
				setActionSelectPos(null);
			}
		}
	}

	public void unitIntentChanged(Class actionIntent) {
		setActionSelectPos(null);
	}

//		public static void updateRange(int range, Vector2 pos) {
//		rangeIndicator = range;
//		selectedPosition = pos;
//	}

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
				interactionPresenter.endTurn();
			}
		};
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
	 * Handle input
	 */
	private void handleInput() {
		/* Check pos touched */
		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
			camera.unproject(cursorPos);
			// Check if any buttons are pushed
			if (actionButtons.getBounds().contains(cursorPos.x, cursorPos.y)) {
				actionButtons.handleInput(cursorPos);
			}
			endTurnButton.handleInput(cursorPos.x, cursorPos.y);
			//Hitboxes for the different intentions
//			if (cursorPos.x <= 106 && cursorPos.x >= 68) {
//				if (cursorPos.y <= 64 && cursorPos.y >= 0) {
//					UI.getInteractionPresenter().updateIntent(ActionComponent.class);
//				} else if (cursorPos.y <= 128 && cursorPos.y >= 64) {
//					UI.getInteractionPresenter().updateIntent(ActionComponentAttack.class);
//				} else if (cursorPos.y <= 192 && cursorPos.y >= 128) {
//					UI.getInteractionPresenter().updateIntent(MovementComponent.class);
//				}
//			}
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
		if (selectedEntity != null) {
			actionButtons.render(spriteBatch, delta);
		}
		if (actionSelectBounds.y > 0) spriteBatch.draw(
			actionSelectTex,
			actionSelectBounds.x, actionSelectBounds.y,
			actionSelectBounds.width, actionSelectBounds.height
		);
		spriteBatch.draw(
			endTurnButton.getTexture(),
			endTurnButton.getBounds().x, endTurnButton.getBounds().y,
			endTurnButton.getBounds().width, endTurnButton.getBounds().height
		);

		if (playerNumberTex != null) {
			spriteBatch.draw(
				playerNumberTex,
				mapBounds.x + 15, renderHeight - playerNumberTex.getHeight() - 15,
				playerNumberTex.getWidth(), playerNumberTex.getHeight()
			);
		}

		spriteBatch.end();
		//Todo: add "selected" indicator
//		if (rangeIndicator > 0 && selectedPosition != null) {
//			shape.setColor(0.1f,0.1f,0.1f,1);
//			shape.begin(ShapeRenderer.ShapeType.Line);
//			shape.circle(selectedPosition.x + map.getMapWidthInPixels() / 2f, selectedPosition.y + map.getMapHeightInPixels() / 2f, rangeIndicator);
//			shape.end();
//		}
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
	}
}
