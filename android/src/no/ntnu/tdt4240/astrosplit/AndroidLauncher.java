package no.ntnu.tdt4240.astrosplit;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import no.ntnu.tdt4240.astrosplit.AstroSplit;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useAccelerometer = false;
		config.useCompass = false;
//		config.hideStatusBar = true;
		config.useImmersiveMode = true;

		initialize(new AstroSplit(), config);
	}
}
