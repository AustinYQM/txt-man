package com.yqmonline.systems

import com.yqmonline.entities.Player
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.position
import com.yqmonline.extensions.tryActionsOn
import com.yqmonline.messages.MoveCamera
import com.yqmonline.messages.MoveTo
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.MessageResponse
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response

object Movable : MessageFacet<MoveTo>(MoveTo::class) {
    override suspend fun receive(message: MoveTo): Response {
        val (context, entity, position) = message
        val world = context.world
        var result: Response = Pass
        val previousPosition = entity.position
        world.fetchBlockAtOrNull(position)?.let { block ->
            if (block.isOccupied) {
                result = entity.tryActionsOn(context, block.occupier.get())
            } else {
                if (world.moveEntity(entity, position)) {
                    result = Consumed
                    if (entity.type == Player) {
                        result =
                            MessageResponse(
                                MoveCamera(
                                    context = context,
                                    source = entity,
                                    previousPosition = previousPosition,
                                ),
                            )
                    }
                }
            }
        }
        return result
    }
}
