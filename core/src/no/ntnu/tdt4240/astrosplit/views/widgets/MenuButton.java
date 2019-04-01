package no.ntnu.tdt4240.astrosplit.views.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class MenuButton {

	private Rectangle bounds;
	private Texture texture;


	/* Constructors */

	/**
	 * Creates a button
	 * @param texture Button texture
	 */
	public MenuButton(Texture texture) {
		this(texture.getWidth() / 2f, texture.getHeight() / 2f, texture);
	}

	/**
	 * Creates a button
	 * @param xCenter X position of button center
	 * @param yCenter Y position of button center
	 * @param texture Button texture
	 */
	public MenuButton(float xCenter, float yCenter, Texture texture) {
		// x and y coords are centered on the button
		this(new Rectangle(
			xCenter - (texture.getWidth() / 2f),
			yCenter - (texture.getHeight() / 2f),
			texture.getWidth(),
			texture.getHeight()
			), texture
		);
	}

	/**
	 * Creates a button
	 * @param bounds Button draw box
	 * @param texture Button texture
	 */
	public MenuButton(Rectangle bounds, Texture texture) {
		this.bounds = bounds;
		this.texture = texture;
	}


	/* Public methods */

	public void setCenterPosition(float x, float y) {
		bounds.x = x - texture.getWidth() / 2f;
		bounds.y = y - texture.getHeight() / 2f;
	}

	public Texture getTexture() {
		return texture;
	}

	public Rectangle getBounds(){
		return bounds;
	}

	public boolean handleInput(float cursorX, float cursorY) {
		if (bounds.contains(cursorX, cursorY)) {
			this.click();
			return true;
		}
		return false;
	}

	public void click() {
		// Override this method
	}

	public void update(float deltaTime) {
		// Possibly update animation. Would require texture[] and cycle time
	}

	public void dispose(){
		texture.dispose();
	}
}
