package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;


class MainMenuSubView implements SubView {

//	private MenuPresenter menuPresenter;

	private ButtonList buttonList;


	MainMenuSubView(Rectangle bounds) {

		// Create buttons
		buttonList = new ButtonList(bounds,
			new MenuButton[] {
				new MenuButton(new Texture("Astro/buttonStart.png")) {
					@Override
					public void click() {
						// Start game
						ViewStateManager.getInstance().setScreen(new GameView());
					}
				},
				new MenuButton(new Texture("Astro/buttonSettings.png")) {
					@Override
					public void click() {
						// Settings
						System.out.println("Chose: Settings");
					}
				},
				new MenuButton(new Texture("Astro/buttonQuit.png")) {
					@Override
					public void click() {
						// Quit
						Gdx.app.exit();
					}
				}
			}
		);
	}

	public void handleInput(Vector3 cursor) {
		buttonList.handleInput(cursor);
	}

	public void render(SpriteBatch sb, float deltaTime) {
		buttonList.render(sb, deltaTime);
	}

	public void dispose() {
		buttonList.dispose();
	}
}
