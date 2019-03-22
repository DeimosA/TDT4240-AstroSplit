package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.AstroSplit;
import no.ntnu.tdt4240.astrosplit.models.EiluMenuButtons;

public class EiluMenuState {

	/* <Textures> */

	// background
	private Texture background;
	private Texture ground;
	// buttons
	private EiluMenuButtons buttonTask1;
	private EiluMenuButtons buttonTask2;
	private EiluMenuButtons buttonTask3;
	private EiluMenuButtons buttonTask4;
	private EiluMenuButtons buttonQuit;

	// </textures>

	// divide screen
	private int windowHeight;
	private int amountOfRows;
	private int divideHeightByRows;

	// positions
	private int posOne;
	private int posTwo;
	private int posThree;
	private int posFour;
	private int posFive;
	private int posSix;
	private int posSeven;

	public EiluMenuState(ViewStateManager gsm) {
		super(gsm);

		/* Divide screen */
		amountOfRows = 8; //BUTTONS(+1 for margin bottom)
		windowHeight = AstroSplit.HEIGHT; //HEIGHT
		divideHeightByRows = windowHeight / amountOfRows; //CREATE POSITION(480 / 4 = 120)

		/* Positions */
		posOne = windowHeight - divideHeightByRows; //y minus acquired position, start top, draw downwards
		posTwo = windowHeight - (divideHeightByRows * 2); //minus, go down
		posThree = windowHeight - (divideHeightByRows * 3);
		posFour = windowHeight - (divideHeightByRows * 4);
		posFive = windowHeight - (divideHeightByRows * 5);
		posSix = windowHeight - (divideHeightByRows * 6);
		posSeven = windowHeight - (divideHeightByRows * 7);

		/* Buttons */
		buttonTask1 = new EiluMenuButtons(AstroSplit.WIDTH / 2, posTwo, 1); //i = button number/texture
		buttonTask2 = new EiluMenuButtons(AstroSplit.WIDTH / 2, posThree, 2);
		buttonTask3 = new EiluMenuButtons(AstroSplit.WIDTH / 2, posFour, 3);
		buttonTask4 = new EiluMenuButtons(AstroSplit.WIDTH / 2, posFive, 4);
		buttonQuit = new EiluMenuButtons(AstroSplit.WIDTH / 2, posSeven, 5);

		// initialize camera to be able to read x,y input
		cam.setToOrtho(false, AstroSplit.WIDTH, AstroSplit.HEIGHT);

		background = new Texture("background.png");
		ground = new Texture("ground.png");
		groundPos = new Vector2(cam.viewportWidth, cam.viewportHeight);
	}

	@Override
	public void handleInput() {

		/* Texture pos touched */
		if (Gdx.input.justTouched()) {
			Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(tmp);


			/* Task 1 */
			if (buttonTask1.getBounds().contains(tmp.x, tmp.y) || Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
				System.out.println("Chose: Task 1");
				gsm.set(new PlayStateTask1(gsm));

				/* Task 2 */
			} else if (buttonTask2.getBounds().contains(tmp.x, tmp.y)) {
				System.out.println("Chose: Task 2");
				gsm.set(new PlayStateTask2(gsm));

				/* Task 3 */
			} else if (buttonTask3.getBounds().contains(tmp.x, tmp.y)) {
				System.out.println("Chose: Task 3");
				gsm.set(new PlayStateTask3(gsm));

				/* Task 4 */
			} else if (buttonTask4.getBounds().contains(tmp.x, tmp.y)) {
				System.out.println("Chose: Task 4");
				gsm.set(new PlayStateTask4(gsm));

				/* Quit */
			} else if (buttonQuit.getBounds().contains(tmp.x, tmp.y)) {
				System.out.println("Quit: Game Closed");

				Gdx.app.exit();
			}
		}

		/* Key pressed */
		/* Task 1 */
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			// you are touching your texture
			System.out.println("Chose: Task 1");
			gsm.set(new PlayStateTask1(gsm));

			/* Task 2 */
		} else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			System.out.println("Chose: Task 2");
			gsm.set(new PlayStateTask2(gsm));

			/* Task 3 */
		} else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
			System.out.println("Chose: Task 3");
			gsm.set(new PlayStateTask3(gsm));

			/* Task 4 */
		} else if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
			System.out.println("Chose: Task 4");
			gsm.set(new PlayStateTask4(gsm));

			/* Quit */
		} else if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
			System.out.println("Quit: Game Closed");
			this.dispose();
			Gdx.app.exit();
		}
	}

	@Override
	public void update(float dt) {
		handleInput();

	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);

		sb.begin();
		sb.draw(background, 0, 0, AstroSplit.WIDTH, AstroSplit.HEIGHT);
		sb.draw(ground, 0, 0);
		sb.draw(buttonTask1.getTexture(), (buttonTask1.getPosition().x - (buttonTask1.getButtonWidth() / 2)), buttonTask1.getPosition().y);
		sb.draw(buttonTask2.getTexture(), (buttonTask2.getPosition().x - (buttonTask2.getButtonWidth() / 2)), buttonTask2.getPosition().y);
		sb.draw(buttonTask3.getTexture(), (buttonTask3.getPosition().x - (buttonTask3.getButtonWidth() / 2)), buttonTask3.getPosition().y);
		sb.draw(buttonTask4.getTexture(), (buttonTask4.getPosition().x - (buttonTask4.getButtonWidth() / 2)), buttonTask4.getPosition().y);
		sb.draw(buttonQuit.getTexture(), (buttonQuit.getPosition().x - (buttonQuit.getButtonWidth() / 2)), buttonQuit.getPosition().y);
		sb.end();
	}

	@Override
	public void dispose() {
		background.dispose();
		ground.dispose();

		buttonTask1.dispose();
		buttonTask2.dispose();
		buttonTask3.dispose();
		buttonTask4.dispose();
		buttonQuit.dispose();

		System.out.println("Menu State Disposed");
	}
}
