package com.yqmonline.systems

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.position
import com.yqmonline.extensions.sameLevelNeighborsShuffled
import com.yqmonline.messages.MoveTo
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior

object Wanderer : BaseBehavior<GameContext>() {
    override suspend fun update(
        entity: AnyGameEntity,
        context: GameContext,
    ): Boolean {
        val pos = entity.position
        if (pos.isUnknown.not()) {
            entity.receiveMessage(
                MoveTo(
                    context = context,
                    source = entity,
                    position = pos.sameLevelNeighborsShuffled().first(),
                ),
            )
            return true
        }
        return false
    }
}
