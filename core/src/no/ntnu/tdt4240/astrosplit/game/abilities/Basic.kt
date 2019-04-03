package no.ntnu.tdt4240.astrosplit.game.abilities

import com.badlogic.ashley.core.Entity
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponent
import no.ntnu.tdt4240.astrosplit.game.components.ActionComponentAttack
import no.ntnu.tdt4240.astrosplit.game.components.HealthComponent

object Basic {
    fun attack(attacker: Entity, victim: Entity) {
        val attackComponent: ActionComponentAttack? = attacker.getComponent(ActionComponentAttack::class.java)
        val healthComponent: HealthComponent? = victim.getComponent(HealthComponent::class.java)

        val health = victim.getComponent(HealthComponent::class.java)
        val action = attacker.getComponent(ActionComponentAttack::class.java)
        health.health = health.health - action.damage
    }
}
