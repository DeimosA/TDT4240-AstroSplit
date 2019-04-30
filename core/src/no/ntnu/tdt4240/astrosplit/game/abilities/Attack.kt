package no.ntnu.tdt4240.astrosplit.game.abilities

import com.badlogic.ashley.core.Entity
import no.ntnu.tdt4240.astrosplit.game.components.PositionComponent
import no.ntnu.tdt4240.astrosplit.game.components.TypeComponent
import com.badlogic.gdx.math.Vector2


/*fun attack(attacker:Entity, victim:Entity, type:TypeComponent)
{
    print("Attacker: $attacker\n")
    print("Victim: $victim\n")
    when(type.type)
    {
        "unit" -> Basic.attack(attacker, victim)
    }
}

fun rangeCheck(attacker:Entity, victim: Entity, range:Int):Boolean
{
    val attackerPosition:Vector2   = attacker.getComponent(PositionComponent::class.java).position
    val victimPosition:Vector2     = victim.getComponent(PositionComponent::class.java).position
    if (victimPosition.dst(attackerPosition) <= range)
        return true
    return false
}*/
