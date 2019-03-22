package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Configuration class contains config settings that are stored locally on the users device
 */
public class Configuration implements Model {

    private static final Configuration INSTANCE = new Configuration();
    private Preferences prefStore;

    public final String gameName = "Astro Split";
    public int viewPortRenderHeight = 720;
    public float defaultAspect = 16f/9;


    public static Configuration getInstance() {
        return INSTANCE;
    }

    private Configuration() {

    }

    public void load() {
    	prefStore = Gdx.app.getPreferences("LocalConfig");
	}

	@Override
	public void save() {
		prefStore.flush();
	}

	public int getViewPortRenderWidth() {
		return (int) (viewPortRenderHeight * defaultAspect);
	}
}
