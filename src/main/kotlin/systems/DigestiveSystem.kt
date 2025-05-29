package com.yqmonline.systems

import com.yqmonline.attributes.types.energy
import com.yqmonline.attributes.types.energyLevel
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.isPlayer
import com.yqmonline.messages.Eat
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object DigestiveSystem : MessageFacet<Eat>(Eat::class) {
    /**
     * Receives the given [message].
     * @see Response
     */
    override suspend fun receive(message: Eat): Response {
        val (_, entity, food) = message
        entity.energyLevel.currentEnergy += food.energy
        val verb = if (entity.isPlayer) "You eat" else "The $entity eats"
        logGameEvent("$verb the $food", DigestiveSystem)
        return Consumed
    }
}
