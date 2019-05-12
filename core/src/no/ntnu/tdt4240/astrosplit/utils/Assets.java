package no.ntnu.tdt4240.astrosplit.utils;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Convenience class for asset identifiers
 * Handy for asset manager
 */
public class Assets {

	/* HUD */
	// Backgrounds
	public static final String hud_bg_actions = "Hud/hudActions.png";
	public static final String hud_bg_unitInfo = "Hud/hudUnitInfo.png";
	// Action buttons
	public static final String hud_selectionBox = "Hud/frameSelected.png";
	public static final String hud_button_move = "Hud/buttonMove.png";
	public static final String hud_button_move_disabled = "Hud/buttonMoveDisabled.png";
	public static final String hud_button_sword = "Hud/buttonSword.png";
	public static final String hud_button_sword_disabled = "Hud/buttonSwordDisabled.png";
	public static final String hud_button_heal = "Hud/buttonHeal.png";
	public static final String hud_button_heal_disabled = "Hud/buttonHealDisabled.png";
	public static final String hud_button_endTurn = "Hud/buttonEndTurn.png";
	// Active player indicators
	public static final String hud_Player1_red = "Hud/playerText/player1-red.png";
	public static final String hud_Player2_blue = "Hud/playerText/player2-blue.png";
	public static final String hud_info_hp = "Hud/infoPanel/infoTextHP.png";
	// Unit info

//	public static final String aaa = "";

	/* Units */
	// Grays (aliens)
	public static final String unit_gray_melee = "Units/gray_melee.png";
	public static final String unit_gray_ranged = "Units/gray_ranged.png";
	public static final String unit_gray_medic = "Units/gray_medic.png";
	public static final String unit_gray_melee_text = "Hud/infoPanel/textGrayMelee.png";
	public static final String unit_gray_ranged_text = "Hud/infoPanel/textGrayRanged.png";
	public static final String unit_gray_medic_text = "Hud/infoPanel/textGrayMedic.png";
	// Marines
	public static final String unit_marine_melee = "Units/marine_melee.png";
	public static final String unit_marine_ranged = "Units/marine_ranged.png";
	public static final String unit_marine_medic = "Units/marine_medic.png";
	public static final String unit_marine_melee_text = "Hud/infoPanel/textMarineMelee.png";
	public static final String unit_marine_ranged_text = "Hud/infoPanel/textMarineRanged.png";
	public static final String unit_marine_medic_text = "Hud/infoPanel/textMarineMedic.png";
	// Sectoids
	public static final String unit_sectoid_melee = "Units/sectoid_melee.png";
	public static final String unit_sectoid_ranged = "Units/sectoid_ranged.png";
	public static final String unit_sectoid_medic = "Units/sectoid_medic.png";
	public static final String unit_sectoid_melee_text = "Hud/infoPanel/textSectoidMelee.png";
	public static final String unit_sectoid_ranged_text = "Hud/infoPanel/textSectoidRanged.png";
	public static final String unit_sectoid_medic_text = "Hud/infoPanel/textSectoidMedic.png";
	// Target practice / tutorial assets
	public static final String unit_targetPractice = "Units/tutorial_scarecrow.png";


//	public static final String aaa = "";


	/**
	 * Load assets for in-game UI
	 * @param asm
	 */
	public static void loadHudAssets(AssetManager asm) {

		asm.load(hud_bg_actions, Texture.class);
		asm.load(hud_bg_unitInfo, Texture.class);
		asm.load(hud_selectionBox, Texture.class);
		asm.load(hud_button_move, Texture.class);
		asm.load(hud_button_move_disabled, Texture.class);
		asm.load(hud_button_sword, Texture.class);
		asm.load(hud_button_sword_disabled, Texture.class);
		asm.load(hud_button_heal, Texture.class);
		asm.load(hud_button_heal_disabled, Texture.class);
		asm.load(hud_button_endTurn, Texture.class);
		asm.load(hud_info_hp, Texture.class);

	}

	/**
	 * This is only needed for local multiplayer
	 * @param asm
	 */
	public static void loadHudPlayerIndicators(AssetManager asm) {

		asm.load(hud_Player1_red, Texture.class);
		asm.load(hud_Player2_blue, Texture.class);

	}

	public static void loadGrayUnitAssets(AssetManager asm) {

		asm.load(unit_gray_melee, Texture.class);
		asm.load(unit_gray_ranged, Texture.class);
		asm.load(unit_gray_medic, Texture.class);
		asm.load(unit_gray_melee_text, Texture.class);
		asm.load(unit_gray_ranged_text, Texture.class);
		asm.load(unit_gray_medic_text, Texture.class);

	}

	public static void loadMarineUnitAssets(AssetManager asm) {

		asm.load(unit_marine_melee, Texture.class);
		asm.load(unit_marine_ranged, Texture.class);
		asm.load(unit_marine_medic, Texture.class);
		asm.load(unit_marine_melee_text, Texture.class);
		asm.load(unit_marine_ranged_text, Texture.class);
		asm.load(unit_marine_medic_text, Texture.class);

	}

	public static void loadSectoidUnitAssets(AssetManager asm) {

		asm.load(unit_sectoid_melee, Texture.class);
		asm.load(unit_sectoid_ranged, Texture.class);
		asm.load(unit_sectoid_medic, Texture.class);
		asm.load(unit_sectoid_melee_text, Texture.class);
		asm.load(unit_sectoid_ranged_text, Texture.class);
		asm.load(unit_sectoid_medic_text, Texture.class);

	}

	public static void loadTutorialAssets(AssetManager asm) {

		asm.load(unit_targetPractice, Texture.class);

	}

}
