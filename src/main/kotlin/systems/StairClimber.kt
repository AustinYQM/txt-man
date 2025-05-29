package com.yqmonline.systems

import com.yqmonline.blocks.GameBlock
import com.yqmonline.entities.terrain.StairsUp
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.position
import com.yqmonline.messages.MoveUp
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object StairClimber : MessageFacet<MoveUp>(MoveUp::class) {
    override suspend fun receive(message: MoveUp): Response {
        val (context, player) = message
        val world = context.world
        val playerPos = player.position
        world.fetchBlockAt(playerPos).map { block ->
            if (block.hasStairsUp) {
                logGameEvent("You move up one level...", StairClimber)
                world.moveEntity(player, playerPos.withRelativeZ(1))
                world.scrollOneUp()
            } else {
                logGameEvent("You jump up and try to reach the ceiling. You fail.", StairClimber)
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsUp: Boolean
        get() = this.entities.any { it.type == StairsUp }
}
