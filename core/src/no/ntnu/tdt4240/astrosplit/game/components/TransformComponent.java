package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
	public final Vector2 scale = new Vector2(1.0f, 1.0f);
	public float rotation = 0.0f;
	public int height = 0;
}
