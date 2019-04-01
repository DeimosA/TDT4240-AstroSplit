package no.ntnu.tdt4240.astrosplit.views.widgets;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class ButtonList {


	private Rectangle bounds;
	private MenuButton[] buttons;


	public ButtonList(Rectangle bounds, MenuButton[] buttons) {
		this.bounds = bounds;
		this.buttons = buttons;

		// Calculate positions for buttons within bounds
		float rowHeight = bounds.height / buttons.length;
		float rowCenter = rowHeight / 2;
		float xCenter = bounds.width / 2;
		for (int i = 0; i < buttons.length; i++) {
			int reverseIndex = buttons.length - 1 - i;
			buttons[reverseIndex].setCenterPosition(
				xCenter,
				rowCenter + i * rowHeight
			);
		}
	}

	public int handleInput(Vector3 cursor) {
		// Let's check if we're even in area
		if (bounds.contains(cursor.x, cursor.y)) {
			float x = cursor.x - bounds.x;
			float y = cursor.y - bounds.y;
			// Check each button
			for (int i = 0; i < buttons.length; i++) {
				if (buttons[i].handleInput(x, y)) {
					return i;
				}
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
