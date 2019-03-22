package no.ntnu.tdt4240.astrosplit.views.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MenuButton {

	private Vector2 position;
	private Rectangle bounds;
	private Texture texture;

	public MenuButton(int x, int y, Texture texture) {
		position = new Vector2(x, y);
		this.texture = texture;
		bounds = new Rectangle(
			x - (this.texture.getWidth() /2),
			y,
			this.texture.getWidth(),
			this.texture.getHeight()
		);
	}

	public Vector2 getPosition() {
		return position;
	}

	public Texture getTexture() {
		return texture;
	}

	public Rectangle getBounds(){
		return bounds;
	}

	public int getButtonWidth() {
		return texture.getWidth();
	}

	public void dispose(){
		texture.dispose();
	}
}
