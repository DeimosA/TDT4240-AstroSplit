package no.ntnu.tdt4240.astrosplit.views.widgets;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.models.Configuration;


public class ButtonList {


	private Rectangle bounds;
	private MenuButton[] buttons;


	private int subMenuType;

	private boolean selected = false;

	// teamSelected() positions
	private float colWidth;
	private float colCenter;
	private float yCenter;

	// Viewport
	private int renderWidth;
	private int renderHeight;

	public ButtonList(int subMenuType, Rectangle bounds, MenuButton[] buttons) {
		this.bounds = bounds;
		this.buttons = buttons;
		// Main menu / team select
		this.subMenuType = subMenuType;

		// Calculate positions for buttons within bounds
		float rowHeight = bounds.height / buttons.length;
		float rowCenter = rowHeight / 2;
		float xCenter = bounds.width / 2;

		// Sub menu: Team select
		colWidth = bounds.width / 3;
		colCenter = colWidth / 2;
		yCenter = bounds.height / 2;
		// Viewport X/Y
		renderHeight = Configuration.getInstance().viewPortRenderHeight;
		renderWidth = Configuration.getInstance().getViewPortRenderWidth();


		/* Main menu */
		if (subMenuType == 1) {
			for (int i = 0; i < buttons.length; i++) {
				int reverseIndex = buttons.length - 1 - i;
				buttons[reverseIndex].setCenterPosition(
					xCenter,
					rowCenter + i * rowHeight
				);
			}

			/* Team select */
		} else if (subMenuType == 2) {
			// Title
			buttons[0].setCenterPosition(
				xCenter,
				bounds.getHeight() - buttons[0].getTexture().getHeight()
			);

			// Team
			for (int i = 0; i < 3; i++) {

				buttons[i + 1].setCenterPosition(
					colCenter + i * colWidth,
					yCenter
				);
			}

			// Button: back
			buttons[4].setCenterPosition(
				buttons[4].getTexture().getWidth(),
				buttons[4].getTexture().getHeight()
			);

			// Button: confirm
			buttons[5].setCenterPosition(
				buttons[5].getTexture().getWidth() + bounds.width / 2,
				buttons[5].getBounds().height
			);

			// Button: golden confirm
			buttons[6].setCenterPosition(
				renderWidth - buttons[6].getTexture().getWidth(),
				renderHeight - buttons[6].getTexture().getHeight()
			);
			//Golden frame
			buttons[7].setCenterPosition(
				renderWidth - buttons[7].getTexture().getWidth(),
				renderHeight - buttons[7].getTexture().getHeight()
			);
		}
	}

	// Texture feedback on selection
	public void teamSelected(int buttonNumber) {

		// Draw over existing texture

			//Button: golden confirm
			buttons[6].setCenterPosition(
				buttons[5].getTexture().getWidth() + bounds.width / 2,
				buttons[5].getBounds().height
			);
			//Golden frame
			buttons[7].setCenterPosition(
				colCenter + buttonNumber * colWidth,
				yCenter
			);

			selected = true;
	}

	public boolean getSelected(){
		return selected;
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

			if (subMenuType == 1) {
				sb.draw(
					button.getTexture(),
					bounds.x + bb.x,
					bounds.y + bb.y,
					bb.width,
					bb.height
				);

			} else if (subMenuType == 2) {
				sb.draw(
					button.getTexture(),
					bounds.x + bb.x,
					bounds.y + bb.y,
					bb.width,
					bb.height
				);
			}
		}
	}

	public void dispose() {
		for (MenuButton button : buttons) {
			button.dispose();
		}
	}
}
