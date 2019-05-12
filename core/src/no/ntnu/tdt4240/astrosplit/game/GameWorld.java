package no.ntnu.tdt4240.astrosplit.game;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent;
import no.ntnu.tdt4240.astrosplit.game.components.MovementComponent;
import no.ntnu.tdt4240.astrosplit.game.entities.TargetDummyEntity;
import no.ntnu.tdt4240.astrosplit.game.factories.UnitFactory;
import no.ntnu.tdt4240.astrosplit.game.systems.AbilitySystem;
import no.ntnu.tdt4240.astrosplit.game.systems.MovementSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.RenderingSystem;
import no.ntnu.tdt4240.astrosplit.game.systems.UnitSystem;
import no.ntnu.tdt4240.astrosplit.models.ClassType;
import no.ntnu.tdt4240.astrosplit.models.GameModel;
import no.ntnu.tdt4240.astrosplit.models.LocalGameModel;
import no.ntnu.tdt4240.astrosplit.models.TeamType;
import no.ntnu.tdt4240.astrosplit.models.UnitModel;
import no.ntnu.tdt4240.astrosplit.presenters.InteractionPresenter;
import no.ntnu.tdt4240.astrosplit.utils.Assets;


/**
 * Used to initiate entities in the game world
 */
public class GameWorld {


	private PooledEngine engine;
	private AssetManager assetManager;

	private static final Vector2[] p1DefaultPos = {new Vector2(-16, -112), new Vector2(16, -80), new Vector2(48, -112)};
	private static final Vector2[] p2DefaultPos = {new Vector2(-16, 112), new Vector2(16, 80), new Vector2(48, 112)};

	public GameWorld(PooledEngine engine, Stage stage, AssetManager assetManager) {
		this.engine = engine;
		this.assetManager = assetManager;

		engine.addSystem(new UnitSystem());
		engine.addSystem(new RenderingSystem(new SpriteBatch(), stage));
		engine.addSystem(new MovementSystem());
		engine.addSystem(new AbilitySystem());
		//Todo: Make sure to only load what is used
		Assets.loadMarineUnitAssets(assetManager);
		Assets.loadGrayUnitAssets(assetManager);
		Assets.loadSectoidUnitAssets(assetManager);
		Assets.loadTutorialAssets(assetManager);
		assetManager.finishLoading();
	}

	//this method should create all units to be shown
	public void create() //TODO: Use save or selected teams to create the units
	{

		if (!LocalGameModel.hasOngoingGame()) {

			TeamType[] playerTeams = InteractionPresenter.getPlayerTypes();
			createInitialUnits(playerTeams[0], playerTeams[1]);
		} else {
			createFromSave(InteractionPresenter.getUnits());
		}

	}

	public void createInitialUnits(TeamType p1Team, TeamType p2Team) {
		//Player 1
		UnitFactory.createEntity(engine, assetManager, p1Team, ClassType.MEDIC, p1DefaultPos[0],1);
		UnitFactory.createEntity(engine, assetManager, p1Team, ClassType.MELEE, p1DefaultPos[1],1);
		UnitFactory.createEntity(engine, assetManager, p1Team, ClassType.RANGE, p1DefaultPos[2],1);
		//Player 2
		UnitFactory.createEntity(engine, assetManager, p2Team, ClassType.MEDIC, p2DefaultPos[0],2);
		UnitFactory.createEntity(engine, assetManager, p2Team, ClassType.MELEE, p2DefaultPos[1],2);
		UnitFactory.createEntity(engine, assetManager, p2Team, ClassType.RANGE, p2DefaultPos[2],2);
	}


	public void createTutorialUnits() {
		Entity tutorialUnit = UnitFactory.createEntity(engine, assetManager, TeamType.TEAM_MARINES, ClassType.MELEE, new Vector2(-16, -16), 1);
		tutorialUnit.getComponent(ActionComponent.class).actionPoints = 10000;
		tutorialUnit.getComponent(MovementComponent.class).movementPoints = 10000;
		new TargetDummyEntity().create(engine, assetManager, new Vector2(16, -16), 2, ClassType.DUMMY);
	}

	public void createFromSave(Array<UnitModel> units) {
		System.out.println("Created units from save!");
		for (UnitModel unit : units) {
			UnitFactory.createEntity(engine, assetManager, unit.teamType, unit.classType, unit.pos,unit.player);
		}

	}
}
