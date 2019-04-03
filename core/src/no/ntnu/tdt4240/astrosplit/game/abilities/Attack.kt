package no.ntnu.tdt4240.astrosplit.game.abilities

import com.badlogic.ashley.core.Entity
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent

fun attack(attacker:Entity, victim:Entity, type:TypeComponent)
{
    when(type.type)
    {
        "unit" -> Basic.attack(attacker, victim)
    }
}
