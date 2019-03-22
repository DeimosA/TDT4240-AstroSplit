package no.ntnu.tdt4240.astrosplit.views.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class MenuButton {

	private Rectangle bounds;
	private Texture texture;


	public MenuButton(int x, int y, Texture texture) {
		this.bounds = new Rectangle(
			x - (texture.getWidth() / 2f),
			y,
			texture.getWidth(),
			texture.getHeight()
		);
		this.texture = texture;
	}


	public Texture getTexture() {
		return texture;
	}

	public Rectangle getBounds(){
		return bounds;
	}

	public void dispose(){
		texture.dispose();
	}
}
