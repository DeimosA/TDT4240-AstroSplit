package no.ntnu.tdt4240.astrosplit.views.widgets;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.models.Configuration;


public class ButtonList {


	private Rectangle bounds;
	private MenuButton[] buttons;

	// Rows
	private float rowHeight;
	private float rowCenter;
	private float xCenter;


	public ButtonList(Rectangle bounds, MenuButton[] buttons) {
		this.bounds = bounds;
		this.buttons = buttons;

		// Calculate positions for buttons within bounds
		this.rowHeight = bounds.height / buttons.length;
		this.rowCenter = rowHeight / 2;
		this.xCenter = bounds.width / 2;

		for (int i = 0; i < buttons.length; i++) {
			int reverseIndex = buttons.length - 1 - i;
			buttons[reverseIndex].setCenterPosition(
				xCenter,
				rowCenter + i * rowHeight
			);
		}
	}

	public int handleInput(Vector3 cursor) {
		float x = cursor.x - bounds.x;
		float y = cursor.y - bounds.y;
		// Check each button
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].handleInput(x, y)) {
				return i;
			}
		}
		return -1;
	}

	public void render(SpriteBatch sb, float deltaTime) {
		for (MenuButton button : buttons) {
			Rectangle bb = button.getBounds(); // Button bounds

			sb.draw(
				button.getTexture(),
				bounds.x + bb.x,
				bounds.y + bb.y,
				bb.width,
				bb.height
			);
		}
	}

	public void dispose() {
		for (MenuButton button : buttons) {
			button.dispose();
		}
	}
}
