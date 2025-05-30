package com.yqmonline.systems

import com.yqmonline.attributes.CombatStats
import com.yqmonline.attributes.types.ExperienceGainer
import com.yqmonline.attributes.types.combatStats
import com.yqmonline.attributes.types.experience
import com.yqmonline.events.PlayerGainedLevel
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.attackValue
import com.yqmonline.extensions.defenseValue
import com.yqmonline.extensions.isPlayer
import com.yqmonline.extensions.whenTypeIs
import com.yqmonline.messages.EntityDestroyed
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.zircon.internal.Zircon
import kotlin.math.min

object ExperienceAccumulator : MessageFacet<EntityDestroyed>(EntityDestroyed::class) {
    /**
     * Receives the given [message].
     * @see Response
     */
    override suspend fun receive(message: EntityDestroyed): Response {
        val (_, defender, attacker) = message
        attacker.whenTypeIs<ExperienceGainer> { experienceGainer ->
            val xp = experienceGainer.experience
            val stats = experienceGainer.combatStats
            val defenderHp = defender.findAttribute(CombatStats::class).map { it.maxHp }.orElse(0)
            val amount = (defenderHp + defender.attackValue + defender.defenseValue) - xp.currentLevel * 2
            if (amount > 0) {
                xp.currentXP += amount
                while (xp.currentXP > Math.pow(xp.currentLevel.toDouble(), 1.5) * 20) {
                    xp.currentLevel++
                    logGameEvent("$attacker advanced to level ${xp.currentLevel}.", ExperienceAccumulator)
                    stats.hpProperty.value = min(stats.hp + xp.currentLevel * 2, stats.maxHp)
                    if (attacker.isPlayer) {
                        Zircon.eventBus.publish(PlayerGainedLevel(ExperienceAccumulator))
                    }
                }
            }
        }
        return Consumed
    }
}
