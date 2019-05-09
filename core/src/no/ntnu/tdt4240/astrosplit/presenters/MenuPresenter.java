package no.ntnu.tdt4240.astrosplit.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import no.ntnu.tdt4240.astrosplit.views.View;


public class MenuPresenter {

	private View currentView;

	public MenuPresenter(View currentView) {
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(backInputProcessor);

		this.currentView = currentView;
	}


	private InputAdapter backInputProcessor = new InputAdapter() {
		@Override
		public boolean keyDown(int keycode) {
			// Use escape key or android back to go back to menu or quit from menu
			if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
				currentView.goBack();
				return true;
			}
			return false;
		}
	};


	public void setCurrentView(View currentView) {
		this.currentView = currentView;
	}

	public void dispose() {
		this.currentView = null;
		Gdx.input.setInputProcessor(null);
	}
}
