package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameView implements Screen {

	private static PooledEngine gameEngine = null;

	GameView() {
		Gdx.gl.glClearColor(0.2f, 0.0f, 0.1f, 1);
	}
	
	public static PooledEngine getGameEngine(){
		if (gameEngine == null){
			gameEngine = new PooledEngine();
		}
		return gameEngine;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
