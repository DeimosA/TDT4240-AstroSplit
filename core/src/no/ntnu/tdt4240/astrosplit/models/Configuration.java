package no.ntnu.tdt4240.astrosplit.models;

public class Configuration implements Model {

    private static final Configuration INSTANCE = new Configuration();

    public String gameName = "Astro Split";
    public int viewPortHeight = 720;
    public float defaultAspect = 16f/9;


    public static Configuration getInstance() {
        return INSTANCE;
    }

    private Configuration() {
    	// TODO load saved config
    }

	@Override
	public void save() {
		// TODO save config
	}
}
