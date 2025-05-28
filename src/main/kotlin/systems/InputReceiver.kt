package com.yqmonline.systems

import com.yqmonline.entities.Player
import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.GameEntity
import com.yqmonline.extensions.position
import com.yqmonline.messages.MoveDown
import com.yqmonline.messages.MoveTo
import com.yqmonline.messages.MoveUp
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.zircon.api.data.Position3D
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
                    KeyCode.KEY_W -> player.moveTo(currentPosition.withRelativeY(-1), context)
                    KeyCode.KEY_A -> player.moveTo(currentPosition.withRelativeX(-1), context)
                    KeyCode.KEY_S -> player.moveTo(currentPosition.withRelativeY(1), context)
                    KeyCode.KEY_D -> player.moveTo(currentPosition.withRelativeX(1), context)
                    KeyCode.KEY_R -> player.moveUp(context)
                    KeyCode.KEY_F -> player.moveDown(context)
                    else -> currentPosition
                }
        }
        return true
    }

    private suspend fun GameEntity<Player>.moveTo(
        position: Position3D,
        context: GameContext,
    ) {
        receiveMessage(MoveTo(context, this, position))
    }

    private suspend fun GameEntity<Player>.moveUp(context: GameContext) {
        receiveMessage(MoveUp(context, this))
    }

    private suspend fun GameEntity<Player>.moveDown(context: GameContext) {
        receiveMessage(MoveDown(context, this))
    }
}
