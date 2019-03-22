package no.ntnu.tdt4240.astrosplit;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.views.ViewStateManager;

public class AstroSplit implements ApplicationListener {

//	Texture img;
	private ViewStateManager vsm;
	private SpriteBatch batch;

	@Override
	public void create () {
		vsm = ViewStateManager.getInstance();
		batch = new SpriteBatch();
		Configuration.getInstance().load();
//		img = new Texture("badlogic.jpg");
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		vsm.get().render(Gdx.graphics.getDeltaTime());
//		batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
