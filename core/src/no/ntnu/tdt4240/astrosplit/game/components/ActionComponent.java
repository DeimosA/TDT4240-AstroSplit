package no.ntnu.tdt4240.astrosplit.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

public class ActionComponent implements Component{
	public ArrayList<Entity> attackList = new ArrayList<Entity>();
	public ArrayList<Entity> healList = new ArrayList<Entity>();
}
