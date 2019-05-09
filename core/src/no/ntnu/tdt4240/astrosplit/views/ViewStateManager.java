package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Screen;

public final class ViewStateManager {

	private static ViewStateManager INSTANCE = null;

	private Screen currentScreen;
	private Screen oldScreen = null; // Keep so we can dispose at end of renderloop

	/**
	 * Manages the current view
	 */
	private ViewStateManager() {
		currentScreen = new GameView();
	} //should be MenuView

	/**
	 * Get the singular instance
	 * @return VSM instance
	 */
	public static ViewStateManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ViewStateManager();
		}
		return INSTANCE;
	}

	/**
	 * Get the current view
	 * @return
	 */
	public Screen get() {
		return currentScreen;
	}

	/**
	 * Set the current view
	 * Package protected
	 * @param newScreen
	 */
	void setScreen(Screen newScreen) {
		oldScreen = currentScreen;
		currentScreen = newScreen;
	}

	public void disposeOldScreen() {
		if (oldScreen != null) {
			oldScreen.dispose();
			oldScreen = null;
		}
	}
}
