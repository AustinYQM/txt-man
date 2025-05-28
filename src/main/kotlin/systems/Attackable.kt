package com.yqmonline.systems

import com.yqmonline.extensions.MessageFacet
import com.yqmonline.messages.Attack
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object Attackable : MessageFacet<Attack>(Attack::class) {
    override suspend fun receive(message: Attack): Response {
        val (context, _, target) = message
        context.world.removeEntity(target)
        return Consumed
    }
}
