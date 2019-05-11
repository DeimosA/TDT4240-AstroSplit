package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

import no.ntnu.tdt4240.astrosplit.models.LocalGameModel;
import no.ntnu.tdt4240.astrosplit.utils.Assets;


class LocalGameView implements Screen {


	private LocalGameModel gameModel;
	private GameView viewPlayer1;
	private GameView viewPlayer2;

	private AssetManager assetManager;


	LocalGameView(LocalGameModel gameModel) {
		Gdx.gl.glClearColor(1, 0, 0, 1);

//		assetManager = new AssetManager();
//		Assets.loadHudPlayerIndicators(assetManager);
//		assetManager.finishLoading();

//		viewPlayer1 = new GameView(gameModel, assetManager);

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
