package no.ntnu.tdt4240.astrosplit.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack;
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentHeal;
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent;
import no.ntnu.tdt4240.astrosplit.game.components.PlayerComponent;
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.presenters.MenuPresenter;
import no.ntnu.tdt4240.astrosplit.utils.Assets;


public class UnitInfoSubView extends SubView {

	// Assets
	private Texture unitPortrait;
	private Texture unitType;

	private Entity selectedEntity;
	private BitmapFont font;
	private AssetManager assetManager;


	/**
	 * Sub view for unit info
	 * @param bounds
	 */
	UnitInfoSubView(Rectangle bounds, AssetManager assetManager) {
		super(bounds);

		font = new BitmapFont();
		font.getData().setScale(2f);

		this.assetManager = assetManager;
	}

	void setSelectedUnit(Entity unit) {
		selectedEntity = unit;
	}

	@Override
	void handleInput(Vector3 cursor) {
	}

	@Override
	void render(SpriteBatch sb, float deltaTime) {
		if (selectedEntity != null) {

			float x = bounds.x + 50;
			float y = 450;
			float spacing = 50;
			int n = 0;
			float scale = 1.2f;

			// Unit type
			TypeComponent typeComponent = selectedEntity.getComponent(TypeComponent.class);
			switch (typeComponent.teamType) {
				case TEAM_GRAYS:

					switch (typeComponent.unitClassType) {
						case MELEE:
							unitType = assetManager.get(Assets.unit_gray_melee_text);
							break;
						case RANGE:
							unitType = assetManager.get(Assets.unit_gray_ranged_text);
							break;
						case MEDIC:
							unitType = assetManager.get(Assets.unit_gray_medic_text);
							break;
					}

					break;
				case TEAM_MARINES:

					switch (typeComponent.unitClassType) {
						case MELEE:
							unitType = assetManager.get(Assets.unit_marine_melee_text);
							break;
						case RANGE:
							unitType = assetManager.get(Assets.unit_marine_ranged_text);
							break;
						case MEDIC:
							unitType = assetManager.get(Assets.unit_marine_medic_text);
							break;
					}

					break;
				case TEAM_SECTOIDS:

					switch (typeComponent.unitClassType) {
						case MELEE:
							unitType = assetManager.get(Assets.unit_sectoid_melee_text);
							break;
						case RANGE:
							unitType = assetManager.get(Assets.unit_sectoid_ranged_text);
							break;
						case MEDIC:
							unitType = assetManager.get(Assets.unit_sectoid_medic_text);
							break;
					}

					break;
				default:
					unitType = null;
			}
			if (unitType != null) {
				sb.draw(
					unitType,
					x - 44,
					y - spacing * n,
					unitType.getWidth() * scale,
					unitType.getHeight() * scale
				);
				n++;
			}
			// Player number
			font.draw(
				sb,
				"Player: " + selectedEntity.getComponent(PlayerComponent.class).id,
				x,
				y - spacing * n
			);
			n++;
			// Health
			HealthComponent healthComponent = selectedEntity.getComponent(HealthComponent.class);
			font.draw(
				sb,
				"HP: " + healthComponent.health + "/" + healthComponent.maxHealth,
				x,
				y - spacing * n
			);
			n++;
			// Damage
			ActionComponentAttack actionComponentAttack = selectedEntity.getComponent(ActionComponentAttack.class);
			if (actionComponentAttack != null) {
				font.draw(
					sb,
					"Damage: " + actionComponentAttack.damage,
					x,
					y - spacing * n
				);
				n++;
			}
			// Heal
			ActionComponentHeal actionComponentHeal = selectedEntity.getComponent(ActionComponentHeal.class);
			if (actionComponentHeal != null) {
				font.draw(
					sb,
					"Heal: " + actionComponentHeal.heal,
					x,
					y - spacing * n
				);
				n++;
			}
		}
	}

	@Override
	void dispose() {
	}

	@Override
	boolean goBack() {
		return true;
	}
}
