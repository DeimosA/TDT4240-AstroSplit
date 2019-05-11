package no.ntnu.tdt4240.astrosplit.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.tdt4240.astrosplit.models.Configuration;
import no.ntnu.tdt4240.astrosplit.models.Model;

/**
 * Configuration class contains config settings that are stored locally on the users device.
 * Singleton because we only want one of this
 */
public final class AudioManager implements Model {

	private static AudioManager INSTANCE = null;

	//		private Preferences prefStore;
	private Configuration config;

	/* Music */
	private Music musicMenu;

	/* Sound */
	private Sound soundButton;


	// Disposable arrays
	private List<Music> musicArray = new ArrayList();
	private List<Sound> soundArray = new ArrayList();

	/**
	 * Get the singular sound instance
	 *
	 * @return
	 */
	public static AudioManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AudioManager();
		}
		return INSTANCE;
	}

	/**
	 * Private so that singleton works
	 */
	private AudioManager() {
		config = Configuration.getInstance();
		load();
	}

	/**
	 * Get music preference (on/off)
	 *
	 */

	private boolean isMusicOn() {
		return config.isMusicOn();
	}

	/**
	 * Get sound preference (on/off)
	 *
	 */
	private boolean isSoundEffectsOn() {
		return config.isSoundEffectsOn();
	}

	@Override
	public void load() {
		/* TODO Load all assets, add to array */
		musicMenu = Gdx.audio.newMusic(Gdx.files.internal("Audio/musicPlaceholder.mp3"));
		musicArray.add(musicMenu);

		soundButton = Gdx.audio.newSound(Gdx.files.internal("Audio/soundButton.wav"));
		soundArray.add(soundButton);
	}

	@Override
	public void save() {
	}

	/**
	 * Play music
	 */
	public void PlayMusicMenu() {
		/* Main menu  */
		musicMenu.setLooping(false); //For now
		musicMenu.setVolume(0.5f); //10%
		musicMenu.play();
	}

	/**
	 * Stop music
	 */
	public void stopMusicMenu() {
		if (!config.isMusicOn()) {
			musicMenu.stop();
		}
	}

	/**
	 * Play sound effects
	 */
	/* Menu button sound (Menuview.handleInput() */
	public void playSoundButton() {
		if(config.isSoundEffectsOn()) {
			soundButton.play();
		}
	}

	/**
	 * Dispose audio assets
	 */
	public void dispose() {
		if (musicArray != null) {
			for (Music music : musicArray) {
				music.dispose();
				System.out.println("Music disposed");
			}
		}

		if (soundArray != null) {
			for (Sound sound : soundArray) {
				System.out.println("Sound disposed");
				sound.dispose();
			}
		}
//		System.out.println("Audio disposed");
	}
}
