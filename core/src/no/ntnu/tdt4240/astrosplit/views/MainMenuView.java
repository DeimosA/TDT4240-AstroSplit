package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;

class MainMenuView implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Vector2 cursorPos = new Vector2();

	// Divide screen
	private int renderHeight;
	private int renderWidth;
	private int rowHeight;

	// Menu background
	private Texture background;
	private Texture ground;

	// Buttons
	private int buttonCount = 3;
	private MenuButton[] buttons;


	MainMenuView() {
		Gdx.gl.glClearColor(0.8f, 0.1f, 0.1f, 1);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, renderWidth, renderHeight);
		spriteBatch = new SpriteBatch();

		/* Divide screen in rows */
		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();
		rowHeight = renderHeight / (buttonCount + 2);

		/* Buttons */
		buttons = new MenuButton[buttonCount];
		buttons[0] = new MenuButton(
			renderWidth / 2,
			renderHeight - (rowHeight * 2),
			new Texture("buttonTask1.png")
		);
		buttons[1] = new MenuButton(
			renderWidth / 2,
			renderHeight - (rowHeight * 3),
			new Texture("buttonTask2.png")
		);
		buttons[2] = new MenuButton(
			renderWidth / 2,
			renderHeight - (rowHeight * 4),
			new Texture("buttonQuit.png")
		);

		/* Menu background */
		background = new Texture("background.png");
		ground = new Texture("ground.png");
	}

	private void handleInput() {

		/* Texture pos touched */
		if (Gdx.input.justTouched()) {
			cursorPos.x = Gdx.input.getX();
			cursorPos.y = Gdx.input.getY();
//			camera.unproject(cursorPos);

			if (buttons[0].getBounds().contains(cursorPos)) {
				/* Task 1 */
				System.out.println("Chose: Task 1");
//				gsm.set(new PlayStateTask2(gsm));

			} else if (buttons[0].getBounds().contains(cursorPos)) {
				/* Task 2 */
				System.out.println("Chose: Task 2");
//				gsm.set(new PlayStateTask2(gsm));

			} else if (buttons[2].getBounds().contains(cursorPos)) {
				/* Quit */
				System.out.println("Chose: Quit game");
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

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0, renderWidth, renderHeight);
		spriteBatch.draw(ground, 0, 0);
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
		ground.dispose();
		for (MenuButton button : buttons) {
			button.dispose();
		}
		System.out.println("Menu State Disposed");
	}
}
