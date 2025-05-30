package com.yqmonline.systems

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.position
import com.yqmonline.messages.MoveTo
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior

object HunterSeeker : BaseBehavior<GameContext>() {
    /**
     * Updates the given [entity] using the given [context].
     * @return `true` if this [Behavior] was performed successfully,`false` otherwise.
     */
    override suspend fun update(
        entity: AnyGameEntity,
        context: GameContext,
    ): Boolean {
        val (world, _, _, player) = context
        var hunted = false
        val path = world.findPath(entity, player)

        if (path.isNotEmpty()) {
            entity.receiveMessage(
                MoveTo(
                    context = context,
                    source = entity,
                    position = path.iterator().next().toPosition3D(player.position.z),
                ),
            )
            hunted = true
        }
        return hunted
    }
}
