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
	private Configuration config;

	/* Music */
	private Music musicMenu;
	private Music musicGame;
	private Music musicGameOver;

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
	private boolean isMusicEnabled() {
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
		/* Main menu */
		musicMenu = Gdx.audio.newMusic(Gdx.files.internal("Audio/musicMenu.mp3"));
		musicArray.add(musicMenu);

		/* In-game */
		musicGame = Gdx.audio.newMusic(Gdx.files.internal("Audio/musicGame.mp3"));
		musicArray.add(musicGame);

		/* Victory */
		musicGameOver = Gdx.audio.newMusic(Gdx.files.internal("Audio/musicGameOver.mp3"));
		musicArray.add(musicGameOver);

		/* Button click sound */
		soundButton = Gdx.audio.newSound(Gdx.files.internal("Audio/soundButton.mp3"));
		soundArray.add(soundButton);
	}

	@Override
	public void save() {
	}

	/**
	 * Play menu music
	 */
	public void playMusicMenu() {
		/* Main menu  */
		if(musicGameOver.isPlaying()){
			musicGameOver.stop();
		} else if(musicGame.isPlaying()){
			musicGame.stop();
		}
		if(isMusicEnabled()) {
			musicMenu.setLooping(false);
			musicMenu.setVolume(0.5f); //50%
			musicMenu.play();
		}
	}

	/**
	 * Play in-game music
	 */
	public void playMusicGame() {
		/* In-game  */
		if(musicMenu.isPlaying()){
			musicMenu.stop();
		}

		if(isMusicEnabled()) {
			musicGame.setLooping(true);
			musicGame.setVolume(0.5f); //50%
			musicGame.play();
		}
	}

	/**
	 * Play game over music
	 */
	public void playMusicGameOver() {
		/* In-game  */
		if(musicGame.isPlaying()){
			musicGame.stop();
		}

		if(isMusicEnabled()) {
			musicGameOver.setLooping(true);
			musicGameOver.setVolume(0.5f); //50%
			musicGameOver.play();
		}
	}

	/**
	 * Stop menu or in-game music
	 */
	public void stopMusic() {
			if(musicMenu.isPlaying()) {
				musicMenu.stop();
			}
			else if(musicGame.isPlaying()){
				musicGame.stop();
			}
			else if(musicGameOver.isPlaying()){
				musicGameOver.stop();
			}
	}

	/**
	 * Play sound effects
	 */
	/* Menu button sound (Menuview.handleInput() */
	public void playSoundButton() {
		if(isSoundEffectsOn()) {
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
			}
		}

		if (soundArray != null) {
			for (Sound sound : soundArray) {
				sound.dispose();
			}
		}
		System.out.println("Audio disposed");
	}
}
