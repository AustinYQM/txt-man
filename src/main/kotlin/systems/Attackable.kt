package com.yqmonline.systems

import com.yqmonline.attributes.types.combatStats
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.hasNoHealthLeft
import com.yqmonline.extensions.isPlayer
import com.yqmonline.messages.Attack
import com.yqmonline.messages.Destroy
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import kotlin.math.max

object Attackable : MessageFacet<Attack>(Attack::class) {
    override suspend fun receive(message: Attack): Response {
        val (context, attacker, target) = message

        return if (attacker.isPlayer || target.isPlayer) {
            val damage = max(0, attacker.combatStats.attackValue - target.combatStats.defenseValue)
            val finalDamage = (Math.random() * damage).toInt() + 1
            target.combatStats.hp -= finalDamage

            logGameEvent("The $attacker hits the $target for $finalDamage!", Attackable)

            if (target.hasNoHealthLeft()) {
                target.sendMessage(
                    Destroy(
                        context = context,
                        source = attacker,
                        target = target,
                        cause = "a blow to the head",
                    ),
                )
            }
            Consumed
        } else {
            Pass
        }
    }
}
