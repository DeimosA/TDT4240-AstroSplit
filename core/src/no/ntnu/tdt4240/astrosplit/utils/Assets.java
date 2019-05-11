package no.ntnu.tdt4240.astrosplit.utils;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Convenience class for asset identifiers
 * Handy for asset manager
 */
public class Assets {

	/* HUD */
	private static boolean hudLoaded = false;
	// Backgrounds
	public static final String hud_bg_actions = "Hud/hudActions.png";
	public static final String hud_bg_unitInfo = "Hud/hudUnitInfo.png";
	// Action buttons
	public static final String hud_selectionBox = "Hud/frameSelected.png";
	public static final String hud_button_move = "Hud/buttonMove.png";
	public static final String hud_button_move_disabled = "Hud/buttonMoveDisabled.png";
	public static final String hud_button_sword = "Hud/buttonSword.png";
	public static final String hud_button_sword_disabled = "Hud/buttonSwordDisabled.png";
	public static final String hud_button_endTurn = "Hud/buttonEndTurn.png";
	// Active player indicators
	private static boolean playerIndicatorsLoaded = false;
	public static final String hud_Player1_red = "Hud/playerText/player1-red.png";
	public static final String hud_Player2_blue = "Hud/playerText/player2-blue.png";

//	public static final String aaa = "";

	/* Units */
	// Grays (aliens)
	private static boolean grayUnitsLoaded = false;
	public static final String unit_gray_melee = "Units/gray_melee.png";
	// Marines
	private static boolean marineUnitsLoaded = false;
	public static final String unit_marine_melee = "Units/marine_melee.png";
	public static final String unit_marine_ranged = "Units/marine_ranged.png";
	public static final String unit_marine_medic = "Units/marine_medic.png";
	// Sectoids
	private static boolean sectoidUnitsLoaded = false;
	public static final String unit_sectoid_melee = "Units/sectoid_melee.png";
	// Target practice / tutorial assets
	private static boolean tutorialLoaded = false;
	public static final String unit_targetPractice = "Units/tutorial_scarecrow.png";


//	public static final String aaa = "";


	/**
	 * Load assets for in-game UI
	 * @param asm
	 */
	public static void loadHudAssets(AssetManager asm) {
//		if (hudLoaded) return;

		asm.load(hud_bg_actions, Texture.class);
		asm.load(hud_bg_unitInfo, Texture.class);
		asm.load(hud_selectionBox, Texture.class);
		asm.load(hud_button_move, Texture.class);
		asm.load(hud_button_move_disabled, Texture.class);
		asm.load(hud_button_sword, Texture.class);
		asm.load(hud_button_sword_disabled, Texture.class);
		asm.load(hud_button_endTurn, Texture.class);

		hudLoaded = true;
	}

	/**
	 * This is only needed for local multiplayer
	 * @param asm
	 */
	public static void loadHudPlayerIndicators(AssetManager asm) {
//		if (playerIndicatorsLoaded) return;

		asm.load(hud_Player1_red, Texture.class);
		asm.load(hud_Player2_blue, Texture.class);

		playerIndicatorsLoaded = true;
	}

	public static void loadGrayUnitAssets(AssetManager asm) {
//		if (grayUnitsLoaded) return;

		asm.load(unit_gray_melee, Texture.class);

		grayUnitsLoaded = true;
	}

	public static void loadMarineUnitAssets(AssetManager asm) {
//		if (marineUnitsLoaded) return;

		asm.load(unit_marine_melee, Texture.class);
		asm.load(unit_marine_ranged, Texture.class);
		asm.load(unit_marine_medic, Texture.class);

		marineUnitsLoaded = true;
	}

	public static void loadSectoidUnitAssets(AssetManager asm) {
//		if (sectoidUnitsLoaded) return;

		asm.load(unit_sectoid_melee, Texture.class);

		sectoidUnitsLoaded = true;
	}

	public static void loadTutorialAssets(AssetManager asm) {
//		if (tutorialLoaded) return;

		asm.load(unit_targetPractice, Texture.class);

		tutorialLoaded = true;
	}

}
