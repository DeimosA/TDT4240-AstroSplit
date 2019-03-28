package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.game.World;
import no.ntnu.tdt4240.astrosplit.game.systems.RenderingSystem;
import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.game.systems.UnitSystem;

public class GameView implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Vector3 cursorPos = new Vector3();

	private int renderHeight;
	private int renderWidth;


	private static PooledEngine engine = null;
	private World world;

	//Textures
	private Texture background;
	private Texture unitTexture;

	GameView()
	{

		Gdx.gl.glClearColor(0.8f, 0.1f, 0.1f, 1);
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();


		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();
		camera.setToOrtho(false, renderWidth, renderHeight);

		engine = new PooledEngine();
		this.world = new World();

		engine.addSystem(new UnitSystem(world));
		engine.addSystem(new RenderingSystem(spriteBatch));

		world.create();


		/* Menu background */
		background = new Texture("background.png");

		//pauseSystems();



	}

	private void pauseSystems() {
		engine.getSystem(UnitSystem.class).setProcessing(false);
		engine.getSystem(RenderingSystem.class).setProcessing(false);
	}

	public static PooledEngine getGameEngine(){
		if (engine == null){
			engine = new PooledEngine();
		}
		return engine;
	}

	private void update(float delta)
	{

		engine.update(delta);


		//Can get state of game here
	}

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

	@Override
	public void render(float delta) {
		//handleInput();
		engine.update(delta);
		drawUI();


	}

	private void drawUI() {
		//camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
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
