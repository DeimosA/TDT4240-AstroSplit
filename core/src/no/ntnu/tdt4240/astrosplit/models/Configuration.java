package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

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
//		prefStore = Gdx.app.getPreferences("LocalConfig");
    	// TODO load saved config
    }

	@Override
	public void save() {
		// TODO save config
		prefStore.flush();
	}
}
