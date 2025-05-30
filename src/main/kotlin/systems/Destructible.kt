package com.yqmonline.systems

import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.messages.Destroy
import com.yqmonline.messages.EntityDestroyed
import com.yqmonline.utils.loggerFor
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object Destructible : MessageFacet<Destroy>(Destroy::class) {
    private val LOG = loggerFor<Destructible>()

    override suspend fun receive(message: Destroy): Response {
        LOG.info("Received $message")
        val (context, destroyer, target, cause) = message
        context.world.removeEntity(target)
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        logGameEvent("$target dies $cause.", Destructible)
        return Consumed
    }
}
