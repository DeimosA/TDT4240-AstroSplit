package no.ntnu.tdt4240.astrosplit.presenters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;

import no.ntnu.tdt4240.astrosplit.views.View;


public class MenuPresenter {


	private View currentView;


	public MenuPresenter(View currentView) {
		this.currentView = currentView;
	}

	/**
	 * Set the current view being presented
	 * @param currentView
	 */
	public void setCurrentView(View currentView) {
		this.currentView = currentView;
	}

	/**
	 * Chech if we are on a specified platform
	 * @return
	 */
	public Boolean isOnDesktopPlatform() {
		return Gdx.app.getType() == Application.ApplicationType.Desktop;
	}
	public Boolean isOnAndroidPlatform() {
		return Gdx.app.getType() == Application.ApplicationType.Android;
	}



	public void dispose() {
		this.currentView = null;
	}
}
