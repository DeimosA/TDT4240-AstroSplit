package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.game.World;
import no.ntnu.tdt4240.astrosplit.game.systems.RenderingSystem;
import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.game.systems.UnitSystem;
import no.ntnu.tdt4240.astrosplit.game.Map;

public class GameView implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Vector3 cursorPos = new Vector3();

	private int renderHeight;
	private int renderWidth;


	private static PooledEngine engine = null;
	private World world;


	private Map map;

	GameView()
	{

		Gdx.gl.glClearColor(0.8f, 0.1f, 0.1f, 1);
		spriteBatch = new SpriteBatch();

		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();

		engine = new PooledEngine();
		this.world = new World();

		engine.addSystem(new UnitSystem(world));
		engine.addSystem(new RenderingSystem(spriteBatch));

		world.create();

		this.map = new Map();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,map.getMapWidthInPixels(), map.getMapHeightInPixels());
		camera.position.x = map.getMapWidthInPixels()*0.5f;
		camera.position.y = map.getMapHeightInPixels()*0.5f;

		this.map.setCamera(camera);

	}

	/*
		Pauses all systems connnected to engine
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



	/*
		Dummy inputhandler, has no purpose yet
	 */
	private void handleInput() {

		/* Texture pos touched */
		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
			camera.unproject(cursorPos);

			/*Do something*/
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
		//handleInput();
		camera.update();
		map.render();
		engine.update(delta);	//Will update the RenderingSystem, displaying game characters
		drawUI();	//Overlay


	}

	//Draws overlay UI
	private void drawUI() {
		spriteBatch.begin();
		/*
			Draw some UI
		 */
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {

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

	}
}
