package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.utils.AudioManager;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class GameOverView implements View {


	private OrthographicCamera camera;
	private Viewport viewport;
	private Vector3 cursorPos = new Vector3();

	// Divide screen
	private int renderHeight;
	private int renderWidth;

	// Disposables
	private SpriteBatch spriteBatch;
	private Texture background;
	private Texture title;
	private Texture gameOver;
	private Texture playerWins;

	private Sound sound;

	GameOverView(int playerId) {

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();

		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();
		camera.setToOrtho(false, renderWidth, renderHeight);
		viewport = new FitViewport(renderWidth, renderHeight, camera);

		background = new Texture("Astro/backgroundAstro.png");
		title = new Texture("Astro/logoAstro.png");
		gameOver = new Texture("GameOver/textGameOver.png");

		if (playerId == 1) {
			playerWins = new Texture("GameOver/textPlayer1Wins.png");
		} else {
			playerWins = new Texture("GameOver/textPlayer2Wins.png");
		}

		/* Game over music */
		AudioManager.getInstance().playMusicGameOver();

	}

	private MenuButton[] buttons = new MenuButton[] {
		// Main menu
		new MenuButton(890, 200, new Texture("GameOver/buttonBack.png")) {
			@Override
			public void click() {
				ViewStateManager.getInstance().setScreen(new MenuView());
				AudioManager.getInstance().playMusicMenu();
			}
		},
		// Quit
		new MenuButton(935, 100, new Texture("GameOver/buttonQuit.png")) {
			@Override
			public void click() {
				Gdx.app.exit();
			}
		}
	};

	@Override
	public void goBack() {

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
			camera.unproject(cursorPos);

			// Handle input
			for (MenuButton button : buttons) {
				button.handleInput(cursorPos.x, cursorPos.y);
			}
		}

		/* Render */
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();

		float x = 170;
		float y = 450;
		float spacing = 120;

		// Background and title
		spriteBatch.draw(background, 0, 0, renderWidth, renderHeight);
		spriteBatch.draw(title, 610, 530);
		spriteBatch.draw(gameOver, x, y);
		spriteBatch.draw(playerWins, x, y - spacing);

		// Buttons
		for (MenuButton button : buttons) {
			spriteBatch.draw(
				button.getTexture(),
				button.getBounds().x,
				button.getBounds().y,
				button.getBounds().width,
				button.getBounds().height
			);
		}


		spriteBatch.end();

	}

	@Override
	public void resize(int width, int height) {
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
		background.dispose();
		title.dispose();
		gameOver.dispose();
		playerWins.dispose();
	}
}
