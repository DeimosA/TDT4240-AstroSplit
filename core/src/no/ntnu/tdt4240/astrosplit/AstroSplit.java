package no.ntnu.tdt4240.astrosplit;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.views.ViewStateManager;


public class AstroSplit implements ApplicationListener {

	private ViewStateManager vsm;

	@Override
	public void create () {
		vsm = ViewStateManager.getInstance();
		Configuration.getInstance().load();
	}

	@Override
	public void resize(int width, int height) {
		vsm.get().resize(width, height);
	}

	@Override
	public void render () {
		vsm.get().render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void pause() {
		vsm.get().pause();
	}

	@Override
	public void resume() {
		vsm.get().resume();
	}

	@Override
	public void dispose () {
		vsm.get().dispose();
	}
}
