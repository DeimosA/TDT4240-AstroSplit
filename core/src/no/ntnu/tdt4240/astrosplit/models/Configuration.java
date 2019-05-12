package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import no.ntnu.tdt4240.astrosplit.utils.AudioManager;


/**
 * Configuration class contains config settings that are stored locally on the users device.
 * Singleton because we only want one of this
 */
public class Configuration implements Model {


    private static final Configuration INSTANCE = new Configuration();
    private Preferences prefStore;

    public final String gameName = "Astro Split";
    public final int viewPortRenderHeight = 720;
    public float defaultAspect = 16f/9;

    /* Allowed settings */
	private String fullscreen = "fullscreen";
	private String soundEffectsOn = "soundEffectsOn";
	private String musicOn = "musicOn";


	/**
	 * Get the singular config instance
	 * @return
	 */
	public static Configuration getInstance() {
        return INSTANCE;
    }

	/**
	 * Private so that singleton works
	 */
	private Configuration() {}


	/**
	 * Set the game to use fullscreen mode if available
	 * @param on
	 */
	public void setFullScreen(Boolean on) {

		if (on) {
			// Set FULLSCREEN
			prefStore.putBoolean(fullscreen, true);
			fullscreenMode();

		} else {
			// Set WINDOWED
			prefStore.putBoolean(fullscreen, false);
			windowedMode();
		}

		System.out.println("Fullcreen = " + prefStore.getBoolean(fullscreen));
	}
	public boolean isFullscreen() {
		return prefStore.getBoolean(fullscreen, false);
	}

	/**
	 * Enable or disable sound effects
	 * @param on
	 */
	public void setSoundEffects(Boolean on) {
		prefStore.putBoolean(soundEffectsOn, on);
	}
	public boolean isSoundEffectsOn() {
		return prefStore.getBoolean(soundEffectsOn, true);
	}

	/**
	 * Enable or disable music
	 * @param on
	 */
	public void setMusic(Boolean on) {
		if(on) {
			prefStore.putBoolean(musicOn, true);
			AudioManager.getInstance().playMusicMenu();
		} else {
			prefStore.putBoolean(musicOn, false);
			AudioManager.getInstance().stopMusic();
		}
	}
	public boolean isMusicOn() {
		if(prefStore!=null) {
			return prefStore.getBoolean(musicOn, true);
		}
		return false;
	}

	/**
	 * Get the render width based on the render height and aspect ratio
	 * @return
	 */
	public int getViewPortRenderWidth() {
		return (int) (viewPortRenderHeight * defaultAspect);
	}

	@Override
	public void load() {
		prefStore = Gdx.app.getPreferences("LocalConfig");

		// Apply fullscreen / windowed
		if (Gdx.graphics.supportsDisplayModeChange()) {
			if (prefStore.getBoolean(fullscreen)) {
				fullscreenMode();
			} else {
				windowedMode();
			}
		}

		// Apply music if preferred
		if(prefStore.getBoolean(musicOn)){
			setMusic(true);
		}
	}

	@Override
	public void save() {
		prefStore.flush();
	}

	/**
	 * Set application to fullscreen mode
	 */
	private void fullscreenMode() {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	}
	/**
	 * Set application to windowed mode
	 */
	private void windowedMode() {
		Gdx.graphics.setWindowedMode(getViewPortRenderWidth(), viewPortRenderHeight);
	}
}
