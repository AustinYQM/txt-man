package com.yqmonline.systems

import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.messages.Destroy
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object Destructible : MessageFacet<Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, _, target, cause) = message
        context.world.removeEntity(target)
        logGameEvent("$target dies after receiving $cause", Destructible)
        return Consumed
    }
}
