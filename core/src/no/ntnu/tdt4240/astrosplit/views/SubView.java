package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;


abstract class SubView {

	MenuView menuView;
	ButtonList buttonList;
	Rectangle bounds;

	SubView(Rectangle bounds, MenuView menuView) {
		this.bounds = bounds;
		this.menuView = menuView;
	}

	/**
	 * Get the bounding box for this sub view
	 * @return
	 */
	Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Handle input for the sub view
	 * @param cursor
	 */
	void handleInput(Vector3 cursor) {
		buttonList.handleInput(cursor);
	}

	/**
	 * Render the sub view contents
	 * @param sb		Sprite batch to render with
	 * @param deltaTime	Delta time from previous frame
	 */
	void render(SpriteBatch sb, float deltaTime) {
		buttonList.render(sb, deltaTime);
	}

	/**
	 * Dispose of sub view contents
	 */
	void dispose() {
		buttonList.dispose();
	}

	/**
	 * Go back one menu or action
	 * @return Whether an action was taken or not
	 */
	abstract boolean goBack();
}
