package com.yqmonline.systems

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.position
import com.yqmonline.messages.MoveTo
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent

object InputReceiver : BaseBehavior<GameContext>() {
    override suspend fun update(
        entity: AnyGameEntity,
        context: GameContext,
    ): Boolean {
        val (_, _, uiEvent, player) = context
        val currentPosition = player.position

        if (uiEvent is KeyboardEvent) {
            val newPosition =
                when (uiEvent.code) {
                    KeyCode.KEY_W -> currentPosition.withRelativeY(-1)
                    KeyCode.KEY_A -> currentPosition.withRelativeX(-1)
                    KeyCode.KEY_S -> currentPosition.withRelativeY(1)
                    KeyCode.KEY_D -> currentPosition.withRelativeX(1)
                    else -> currentPosition
                }
            player.receiveMessage(MoveTo(context, player, newPosition))
        }
        return true
    }
}
