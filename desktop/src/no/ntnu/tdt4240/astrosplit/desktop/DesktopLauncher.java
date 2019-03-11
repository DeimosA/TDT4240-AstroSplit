package no.ntnu.tdt4240.astrosplit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.ntnu.tdt4240.astrosplit.AstroSplit;
import no.ntnu.tdt4240.astrosplit.models.Configuration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Configuration gameConfig = Configuration.getInstance();

		config.title = gameConfig.gameName;
		config.height = gameConfig.viewPortRenderHeight;
		config.width = (int) (gameConfig.viewPortRenderHeight * gameConfig.defaultAspect);

		new LwjglApplication(new AstroSplit(), config);
	}
}
