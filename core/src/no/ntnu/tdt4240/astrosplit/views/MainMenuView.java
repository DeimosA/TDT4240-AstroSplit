package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;
import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class MainMenuView implements Screen {

	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch spriteBatch;
	private Vector3 cursorPos = new Vector3();
	private MenuPresenter menuPresenter;

	// Divide screen
	private int renderHeight;
	private int renderWidth;
	private int rowHeight;

	// Menu background
	private Texture background;
	private Texture title;

	// Buttons
	private int buttonCount = 3;
	private MenuButton[] buttons;


	MainMenuView() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();

		menuPresenter = new MenuPresenter(this);

		/* Divide screen in rows */
		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();
		rowHeight = renderHeight / (buttonCount + 3);
		camera.setToOrtho(false, renderWidth, renderHeight);
		viewport = new ExtendViewport(renderWidth, renderHeight, camera);

		/* Buttons */
		buttons = new MenuButton[buttonCount];
		// Start game button
		buttons[0] = new MenuButton(
			renderWidth / 2,
			renderHeight - (rowHeight * 3),
			new Texture("Astro/buttonStart.png")
		);
		buttons[1] = new MenuButton(
			renderWidth / 2,
			renderHeight - (rowHeight * 4),
			new Texture("Astro/buttonSettings.png")
		);
		buttons[2] = new MenuButton(
			renderWidth / 2,
			renderHeight - (rowHeight * 5),
			new Texture("Astro/buttonQuit.png")
		);

		/* Menu background */
		background = new Texture("Astro/backgroundAstro.png");
		/* Title */
		title = new Texture("Astro/logoAstro.png");
	}

	private void handleInput() {

		/* Texture pos touched */
		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
			camera.unproject(cursorPos);

			if (buttons[0].getBounds().contains(cursorPos.x, cursorPos.y)) {
				/* Start game */
				ViewStateManager.getInstance().setScreen(new GameView());

			} else if (buttons[1].getBounds().contains(cursorPos.x, cursorPos.y)) {
				/* Settings */
				System.out.println("Chose: Settings");

			} else if (buttons[2].getBounds().contains(cursorPos.x, cursorPos.y)) {
				/* Quit */
				Gdx.app.exit();
			}
		}
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		handleInput();

		/* Render */
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();

		// Background and title
		spriteBatch.draw(background, 0, 0, renderWidth, renderHeight);
		spriteBatch.draw(title, (renderWidth - title.getWidth()) / 2f, renderHeight - (rowHeight * 1.8f));

		// Buttons
		for (MenuButton button : buttons) {
			Rectangle bounds = button.getBounds();
			spriteBatch.draw(button.getTexture(), bounds.x, bounds.y);
		}

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
		background.dispose();
		for (MenuButton button : buttons) {
			button.dispose();
		}
		System.out.println("Menu State Disposed");
	}
}
