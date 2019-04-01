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

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;


public class MenuView implements Screen {

	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch spriteBatch;
	private Vector3 cursorPos = new Vector3();
	private MenuPresenter menuPresenter;

	// Divide screen
	private int renderHeight;
	private int renderWidth;
	private int padding = 20;
	private int titlePosY;

	// Disposables
	private Texture background;
	private Texture title;
	private SubView subView;


	MenuView() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();

		menuPresenter = new MenuPresenter(this);

		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();
		camera.setToOrtho(false, renderWidth, renderHeight);
		viewport = new ExtendViewport(renderWidth, renderHeight, camera);

		/* Menu background */
		background = new Texture("Astro/backgroundAstro.png");
		/* Title */
		title = new Texture("Astro/logoAstro.png");
		titlePosY = renderHeight - title.getHeight() - padding;

		Rectangle subViewBounds = new Rectangle(padding, padding,
			renderWidth - 2 * padding, titlePosY - 2 * padding
		);
		subView = new MainMenuSubView(subViewBounds);
	}

	private void handleInput() {

		/* Texture pos touched */
		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
			camera.unproject(cursorPos);

			subView.handleInput(cursorPos);
		}
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float deltaTime) {
		handleInput();

		/* Render */
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();

		// Background and title
		spriteBatch.draw(background, 0, 0, renderWidth, renderHeight);
		spriteBatch.draw(title, (renderWidth - title.getWidth()) / 2f, titlePosY);

		subView.render(spriteBatch, deltaTime);

		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
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
		title.dispose();
		subView.dispose();
		System.out.println("Menu State Disposed");
	}
}
