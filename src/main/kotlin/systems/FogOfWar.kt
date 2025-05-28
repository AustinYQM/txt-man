package com.yqmonline.systems

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.position
import com.yqmonline.tiles.GameTileRepository.EMPTY
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.zircon.api.data.Position3D

object FogOfWar : BaseBehavior<GameContext>() {
    override suspend fun update(
        entity: AnyGameEntity,
        context: GameContext,
    ): Boolean {
        val (world, _, _, player) = context
        world.findVisiblePositionsFor(player).forEach { pos ->
            world
                .fetchBlockAt(
                    Position3D.create(
                        x = pos.x,
                        y = pos.y,
                        z = player.position.z,
                    ),
                ).map { block -> block.top = EMPTY }
        }
        return true
    }
}
