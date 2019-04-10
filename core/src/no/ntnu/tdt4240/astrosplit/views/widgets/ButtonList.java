package no.ntnu.tdt4240.astrosplit.views.widgets;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.models.Configuration;


public class ButtonList {


	private Rectangle bounds;
	private MenuButton[] buttons;

	private boolean save = false;
	private boolean selected = false;

	// Row
	private float rowHeight;
	private float rowCenter;
	private float xCenter;

	// Column
	private float colWidth;
	private float colCenter;
	private float yCenter;

	// Viewport
	private int renderWidth;
	private int renderHeight;

	public ButtonList(int subMenuType, Rectangle bounds, MenuButton[] buttons) {
		this.bounds = bounds;
		this.buttons = buttons;

		// Calculate positions for buttons within bounds
		this.rowHeight = bounds.height / buttons.length;
		this.rowCenter = rowHeight / 2;
		this.xCenter = bounds.width / 2;

		// Columns
		this.colWidth = bounds.width / 3;
		this.colCenter = colWidth / 2;
		this.yCenter = bounds.height / 2;
		// Viewport X/Y
		this.renderHeight = Configuration.getInstance().viewPortRenderHeight;
		this.renderWidth = Configuration.getInstance().getViewPortRenderWidth();


		/* Main menu */
		if (subMenuType == 1) {
			mainMenuButtons();
		}

		/* Game mode selection */
		else if (subMenuType == 2) {
			gameModeButtons();
		}

		/* Team selection */
		else if (subMenuType == 3) {
			teamSelectButtons();
		}
	}

	/* Draw buttons */
	private void mainMenuButtons() {
		for (int i = 0; i < buttons.length; i++) {
			int reverseIndex = buttons.length - 1 - i;
			buttons[reverseIndex].setCenterPosition(
				xCenter,
				rowCenter + i * rowHeight
			);
		}
	} //menu 1

	private void gameModeButtons() {
		for (int i = 0; i < buttons.length; i++) {
			int reverseIndex = buttons.length - 1 - i;
			buttons[reverseIndex].setCenterPosition(
				xCenter,
				rowCenter + (i) * rowHeight
			);
		}
		/* Draw outside viewport */
		// White continue button
		buttons[buttons.length - 1].setCenterPosition(
			renderWidth + buttons[0].getTexture().getWidth(),
			renderHeight + buttons[0].getTexture().getHeight()
		);
	} //menu 2

	private void teamSelectButtons() {
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

		/* Draw outside viewport */
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
	} // menu 3

	// Game mode continue
	public void setSave() {
		//test
		save = true;

		// Draw over existing texture
		if (save) {
			//Button: white continue
			buttons[buttons.length - 1].setCenterPosition(
				xCenter,
				rowCenter + (buttons.length - 1) * rowHeight
			);
		}
	}

	public boolean getSave(){
		return save;
	}

	// Team selection feedback
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

	public boolean getSelected() {
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
