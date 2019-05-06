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

	Rectangle getBounds() {
		return bounds;
	}

	void handleInput(Vector3 cursor) {
		buttonList.handleInput(cursor);
	}

	void render(SpriteBatch sb, float deltaTime) {
		buttonList.render(sb, deltaTime);
	}

	void dispose() {
		buttonList.dispose();
	}
}
