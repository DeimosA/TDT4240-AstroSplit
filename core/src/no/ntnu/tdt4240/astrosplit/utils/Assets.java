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
	// Active player indicators
	private static boolean playerIndicatorsLoaded = false;
	public static final String hud_Player1_red = "Hud/playerText/player1-red.png";
	public static final String hud_Player2_blue = "Hud/playerText/player2-blue.png";

//	public static final String aaa = "";

	/* Units */
	private static boolean unitsLoaded = false;
	// Aliens
	public static final String unit_alien_melee = "Units/alien_melee.png";
	// Marines
	public static final String unit_marine_melee = "Units/marine_melee.png";
	// Sectoids
	public static final String unit_sectoid_melee = "Units/sectoid_melee.png";

//	public static final String aaa = "";


	public static void loadHudAssets(AssetManager asm) {
		if (hudLoaded) return;

		asm.load(hud_bg_actions, Texture.class);
		asm.load(hud_bg_unitInfo, Texture.class);
		asm.load(hud_selectionBox, Texture.class);
		asm.load(hud_button_move, Texture.class);
		asm.load(hud_button_move_disabled, Texture.class);
		asm.load(hud_button_sword, Texture.class);
		asm.load(hud_button_sword_disabled, Texture.class);

		hudLoaded = true;
	}

	public static void loadHudPlayerIndicators(AssetManager asm) {
		if (playerIndicatorsLoaded) return;

		asm.load(hud_Player1_red, Texture.class);
		asm.load(hud_Player2_blue, Texture.class);

		playerIndicatorsLoaded = true;
	}

	public static void loadUnitAssets(AssetManager asm) {
		if (unitsLoaded) return;

		asm.load(unit_alien_melee, Texture.class);
		asm.load(unit_marine_melee, Texture.class);
		asm.load(unit_sectoid_melee, Texture.class);

		unitsLoaded = true;
	}

}
