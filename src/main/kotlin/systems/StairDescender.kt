package com.yqmonline.systems

import com.yqmonline.blocks.GameBlock
import com.yqmonline.entities.terrain.StairsDown
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.position
import com.yqmonline.messages.MoveDown
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object StairDescender : MessageFacet<MoveDown>(MoveDown::class) {
    override suspend fun receive(message: MoveDown): Response {
        val (context, player) = message
        val world = context.world
        val playerPos = player.position

        world.fetchBlockAt(playerPos).map { block ->
            if (block.hasStairsDown) {
                logGameEvent("You move down one level...", StairDescender)
                world.moveEntity(player, playerPos.withRelativeZ(-1))
                world.scrollOneDown()
            } else {
                logGameEvent("You attempt to walk down the stairs but there are none. Very comical.", StairDescender)
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsDown: Boolean
        get() = this.entities.any { it.type == StairsDown }
}
