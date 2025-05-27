package com.yqmonline.systems

import com.yqmonline.extensions.MessageFacet
import com.yqmonline.messages.Dig
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object Diggable : MessageFacet<Dig>(Dig::class) {
    override suspend fun receive(message: Dig): Response {
        val (context, _, target) = message
        context.world.removeEntity(target)
        return Consumed
    }
}
