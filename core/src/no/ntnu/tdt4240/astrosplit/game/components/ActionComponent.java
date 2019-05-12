package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;


public class ActionComponent implements Component{

	public Array<Entity> attackList = new Array<Entity>();
	public Array<Entity> healList = new Array<Entity>();

	public int actionPoints = 1;

}
