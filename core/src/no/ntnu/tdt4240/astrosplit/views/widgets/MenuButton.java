package no.ntnu.tdt4240.astrosplit.views.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class MenuButton {

	private Rectangle bounds;
	private Texture texture;
	private Texture disabledTexture;
	private boolean enabled = true;


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
	 * @param texture	Button texture
	 * @param scale		Button scale
	 */
	public MenuButton(Texture texture, float scale) {
		this(texture.getWidth() * scale / 2f, texture.getHeight() * scale / 2f, texture);
		bounds.width *= scale;
		bounds.height*= scale;
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

	/**
	 * Set center position of button
	 * @param x position in x direction
	 * @param y position in y direction
	 */
	public void setCenterPosition(float x, float y) {
		bounds.x = x - bounds.width / 2f;
		bounds.y = y - bounds.height / 2f;
	}

	/**
	 * Set bottom left position of button
	 * @param x position in x direction
	 * @param y position in y direction
	 */
	public void setPosition(float x, float y) {
		bounds.x = x;
		bounds.y = y;
	}

	/**
	 * Set the button texture
	 * @param texture
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	/**
	 * Set an alternative texture for when the button is disabled
	 * @param texture
	 */
	public void setDisabledTexture(Texture texture) {
		this.disabledTexture = texture;
	}

	/**
	 * Get the current texture
	 * @return
	 */
	public Texture getTexture() {
		if (enabled) {
			return texture;
		} else {
			return disabledTexture;
		}
	}

	/**
	 * Get the position and size of the button
	 * @return Rectangle bounding box
	 */
	public Rectangle getBounds(){
		return bounds;
	}

	/**
	 * Enable or disable the button
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		if (disabledTexture != null) { // If there is no disabled texture we don't want to change this
			this.enabled = enabled;
		}
	}

	/**
	 * Check if the coords is within the button and click it
	 * @param cursorX
	 * @param cursorY
	 * @return Whether the button was clicked or not
	 */
	public boolean handleInput(float cursorX, float cursorY) {
		if (enabled && bounds.contains(cursorX, cursorY)) {
			this.click();
			return true;
		}
		return false;
	}

	/**
	 * Click the button
	 */
	public void click() {
		// Override this method
	}

	public void update(float deltaTime) {
		// Possibly update animation. Would require texture[] and cycle time
	}

	/**
	 * Dispose the textures
	 */
	public void dispose(){
		texture.dispose();
		if (disabledTexture != null) {
			disabledTexture.dispose();
		}
	}
}
