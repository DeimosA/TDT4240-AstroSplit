package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import no.ntnu.tdt4240.astrosplit.game.UI;
import no.ntnu.tdt4240.astrosplit.game.World;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.systems.MovementSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.RenderingSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.UnitSystem;
import no.ntnu.tdt4240.astrosplit.game.Map;
import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.models.GameModel;


public class GameView implements Screen {

	/**
	 * Valid types of game
	 */
	public enum GameType {
		TUTORIAL_GAME,
		LOCAL_GAME,
//		ONLINE_GAME,
	}

	// Disposables
	private SpriteBatch spriteBatch;
	private ShapeRenderer shape;
	private Stage stage;
	private Map map;
	private Texture playerNumberTexture;

	private int renderHeight;
	private int renderWidth;

	private OrthographicCamera camera;
	private Viewport viewport;
	private TextureRegion uiTexture;
	private static int rangeIndicator;
	private static Vector2 selectedPosition = null;
	private Vector3 cursorPos = new Vector3();

	private static PooledEngine engine = null;
	private World world;

	// Game type dependent stuff
	private GameType gameType;
	private GameModel gameModel;


	GameView(GameType gameType, GameModel gameModel) {
		this();
		this.gameType = gameType;
		this.gameModel = gameModel;

		switch (gameType) {
			case TUTORIAL_GAME:
				break;

			case LOCAL_GAME:
				gameModel.save();
				break;
		}
	}

	private GameView() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		spriteBatch = new SpriteBatch();
		playerNumberTexture = new Texture("Astro/TeamSelect/headingPlayer1.png");

		/* UI viewport and camera */
		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, renderWidth, renderHeight);
		viewport = new FitViewport(renderWidth, renderHeight, camera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		/* Stage setup */
		map = new Map();
		OrthographicCamera stageCamera = new OrthographicCamera();
		stageCamera.setToOrtho(false, map.getMapWidthInPixels(), map.getMapHeightInPixels());
		map.setCamera(stageCamera);
		Viewport stageViewport = new FitViewport(renderHeight, renderHeight, camera);
		stage = new Stage(stageViewport);
		Gdx.input.setInputProcessor(stage);

		/* Engine and stuff */
		engine = new PooledEngine();
		this.world = new World();
		shape = new ShapeRenderer();
		shape.setProjectionMatrix(camera.combined);

		engine.addSystem(new UnitSystem(world));
		engine.addSystem(new RenderingSystem(new SpriteBatch(), stage));
		engine.addSystem(new MovementSystem());

		world.create();

		UI.Start();
		uiTexture = new TextureRegion(new Texture("UI.png"));
	}

	/*
		Pauses all systems connected to engine
	 */
	private void pauseSystems() {
		for(EntitySystem system : getGameEngine().getSystems())
		{
			system.setProcessing(false);
		}
	}

	public static PooledEngine getGameEngine(){
		if (engine == null){
			engine = new PooledEngine();
		}
		return engine;
	}

	private void handleInput() {

		/* Texture pos touched */
		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
			camera.unproject(cursorPos);
			//Hitboxes for the different intentions
			if (cursorPos.x <= 106 && cursorPos.x >= 68) {
				if (cursorPos.y <= 64 && cursorPos.y >= 0) {
					UI.getInteractionPresenter().updateIntent(ActionComponent.class);
				} else if (cursorPos.y <= 128 && cursorPos.y >= 64) {
					UI.getInteractionPresenter().updateIntent(ActionComponentAttack.class);
				} else if (cursorPos.y <= 192 && cursorPos.y >= 128) {
					UI.getInteractionPresenter().updateIntent(MovementComponent.class);
				}
			}
		}
	}

	@Override
	public void show() {

	}


	/*
		Is called on every tick,
			updates engine,
			draws UI
	 */
	@Override
	public void render(float delta) {
		handleInput();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.getViewport().apply();
		map.render();
		engine.update(delta);	//Will update the RenderingSystem, displaying game characters

		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		viewport.apply(false);
		drawUI();	//Overlay

	}

	//Draws overlay UI
	private void drawUI() {
		spriteBatch.begin();
		spriteBatch.draw(
			playerNumberTexture,
			10, renderHeight - playerNumberTexture.getHeight() - 10,
			playerNumberTexture.getWidth(), playerNumberTexture.getHeight()
		);
		/*
			Draw some UI
		 */
		spriteBatch.draw(uiTexture, 0, 0);
		spriteBatch.end();
		//Todo: add "selected" indicator
		if (rangeIndicator > 0 && selectedPosition != null) {
			shape.setColor(0.1f,0.1f,0.1f,1);
			shape.begin(ShapeRenderer.ShapeType.Line);
			shape.circle(selectedPosition.x + map.getMapWidthInPixels() / 2f, selectedPosition.y + map.getMapHeightInPixels() / 2f, rangeIndicator);
			shape.end();
		}
	}

	public static void updateRange(int range, Vector2 pos) {
		rangeIndicator = range;
		selectedPosition = pos;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(height, height, false);
		stage.getViewport().setScreenPosition((width - stage.getViewport().getScreenWidth()) / 2, 0);
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
		shape.dispose();
		stage.dispose();
		map.dispose();
	}
}
