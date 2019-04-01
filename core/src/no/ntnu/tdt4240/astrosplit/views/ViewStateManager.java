package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Screen;

public final class ViewStateManager {

	private static ViewStateManager INSTANCE = null;

	private Screen currentScreen;

	private ViewStateManager() {
		currentScreen = new MenuView();
	}

	public static ViewStateManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ViewStateManager();
		}
		return INSTANCE;
	}

	public Screen get() {
		return currentScreen;
	}

	void setScreen(Screen newScreen) {
		currentScreen = newScreen;
		// TODO stack screens?
	}
}
