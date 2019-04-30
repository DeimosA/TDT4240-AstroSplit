package no.ntnu.tdt4240.astrosplit.game.abilities

import com.badlogic.ashley.core.Entity
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent

/*object Basic {
    /*
        Reduces health of victim by attackers damage from ActionComponentAttack
        Gives NullPointerException when entity does not have neccessary components
            -ActionComponentAttack
            -HealthComponent
        This is handled in UnitSystem
     */
    fun attack(attacker: Entity, victim: Entity) {
        val attackComponent: ActionComponentAttack = attacker.getComponent(ActionComponentAttack::class.java)
        val healthComponent: HealthComponent = victim.getComponent(HealthComponent::class.java)

        if(rangeCheck(attacker,victim,attackComponent.range))
        {
            val health = victim.getComponent(HealthComponent::class.java)
            val action = attacker.getComponent(ActionComponentAttack::class.java)
            health.health = health.health - action.damage
        }

    }
}*/
