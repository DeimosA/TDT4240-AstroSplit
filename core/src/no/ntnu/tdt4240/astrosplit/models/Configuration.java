package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

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


	/**
	 * Get the singular config instance
	 * @return
	 */
	public static Configuration getInstance() {
        return INSTANCE;
    }

    private Configuration() {
    }

	/**
	 * Set the game to use fullscreen mode if available
	 * @param fullscreen
	 */
	public void setFullScreen(boolean fullscreen) {
    	// TODO something
	}

    @Override
    public void load() {
    	prefStore = Gdx.app.getPreferences("LocalConfig");
	}

	@Override
	public void save() {
		prefStore.flush();
	}

	/**
	 * Get the render width based on the render height and aspect ratio
	 * @return
	 */
	public int getViewPortRenderWidth() {
		return (int) (viewPortRenderHeight * defaultAspect);
	}
}
