package no.ntnu.tdt4240.astrosplit.game.components;
import com.badlogic.ashley.core.Component;

import no.ntnu.tdt4240.astrosplit.models.ClassType;
import no.ntnu.tdt4240.astrosplit.models.TeamType;

public class TypeComponent implements Component
{
//	public String type = "unit";
	public TeamType teamType;
	public ClassType unitClassType;
}
