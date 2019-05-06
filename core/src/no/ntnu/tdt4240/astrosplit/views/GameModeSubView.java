package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.views.widgets.ButtonList;
import no.ntnu.tdt4240.astrosplit.views.widgets.MenuButton;

class GameModeSubView extends SubView {



	GameModeSubView(final Rectangle bounds, final MenuView menuView) {
		super(bounds, menuView);

		/* Game mode selection */
		this.buttonList = new ButtonList(bounds,
			new MenuButton[] {
				// Continue button, white texture if save exists
				new MenuButton(new Texture("Astro/GameModeSelection/buttonContinueGray.png")) {
					@Override
					public void click() {
						// #1 - Continue
						System.out.println("Clicked: Continue");
//						if(buttonList.getSave()){
//							ViewStateManager.getInstance().setScreen(new MenuView(1));
//						}
					}
				},
//					new MenuButton(new Texture("Astro/GameModeSelection/buttonOnline.png")) {
//						@Override
//						public void click() {
//							// #2 - Online
//							System.out.println("Chose: Online");
//							ViewStateManager.getInstance().setScreen(new MenuView(3));
//						}
//					},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonLocal.png")) {
					@Override
					public void click() {
						// #3 - Local
						System.out.println("Chose: Local");
						menuView.setSubView(new TeamSelectSubView(bounds, menuView));
//						ViewStateManager.getInstance().setScreen(new GameView());

					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonTutorial.png")) {
					@Override
					public void click() {
						// #4 - Tutorial
						System.out.println("Chose: Tutorial");

						//test
//						buttonList.setSave();
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonEmpty.png")) {
					@Override
					public void click() {
						// #5 - Empty button
						System.out.println("Clicked: Emtpy button");
					}
				},
				new MenuButton(new Texture("Astro/GameModeSelection/buttonBack.png")) {
					@Override
					public void click() {
						// #6 - Back
						System.out.println("Chose: Back");
						// Main menu
						menuView.setSubView(new MainMenuSubView(bounds, menuView));
					}
				},

//					//White continue texture
//					new MenuButton(new Texture("Astro/GameModeSelection/buttonContinueWhite.png")) {
//						@Override
//						public void click() {
//							// #7 - Continue
//							System.out.println("Chose: Continue");
//						}
//					}
			}
		);
	}

}
