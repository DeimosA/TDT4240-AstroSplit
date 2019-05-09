package no.ntnu.tdt4240.astrosplit.views.widgets;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class ButtonList {


	private Rectangle bounds;
	private MenuButton[] buttons;

	// Rows
	private float rowHeight;
	private float rowCenter;
	private float xCenter;


	/**
	 * Create a vertical list of buttons that are automatically spaced in the available space
	 * @param bounds	The bounding box
	 * @param buttons	Array of buttons
	 */
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

	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Handle input for the button list
	 * @param cursor
	 * @return Button index
	 */
	public int handleInput(Vector3 cursor) {
		// Confine cursor to bounding box
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

	/**
	 * Render the button list
	 * @param sb
	 * @param deltaTime
	 */
	public void render(SpriteBatch sb, float deltaTime) {
		for (MenuButton button : buttons) {
			// Get button bounds
			Rectangle bb = button.getBounds();
			// Draw button
			sb.draw(
				button.getTexture(),
				bounds.x + bb.x,
				bounds.y + bb.y,
				bb.width,
				bb.height
			);
		}
	}

	/**
	 * Dispose of button list contents
	 */
	public void dispose() {
		for (MenuButton button : buttons) {
			button.dispose();
		}
	}
}
