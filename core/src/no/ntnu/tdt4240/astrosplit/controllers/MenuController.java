package no.ntnu.tdt4240.astrosplit.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;


public class MenuController {


	public MenuController() {
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(new BackInputProcessor());
	}


	private class BackInputProcessor extends InputAdapter {
		@Override
		public boolean keyDown(int keycode) {
			// Use escape key or android back to go back to menu or quit from menu
			if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
				Gdx.app.exit();
				return true;
			}
			return false;
		}
	}

}
