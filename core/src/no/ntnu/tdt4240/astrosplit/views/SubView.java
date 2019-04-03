package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public interface SubView {

	void handleInput(Vector3 cursor);

	void render(SpriteBatch sb, float deltaTime);

	void dispose();

}
