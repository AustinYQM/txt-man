package com.yqmonline.systems

import com.yqmonline.attributes.FungusSpread
import com.yqmonline.entities.EntityFactory
import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.position
import com.yqmonline.extensions.tryToFindAttribute
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.zircon.api.data.Size3D

object FungusGrowth : BaseBehavior<GameContext>(FungusSpread::class) {
    override suspend fun update(
        entity: AnyGameEntity,
        context: GameContext,
    ): Boolean {
        val world = context.world
        val fungusSpread = entity.tryToFindAttribute(FungusSpread::class)
        val (spreadCount, maxSpread) = fungusSpread
        return if (spreadCount < maxSpread && Math.random() < 0.015) {
            world
                .findEmptyLocationWithin(
                    offset = entity.position.withRelativeX(-1).withRelativeY(-1),
                    size = Size3D.create(3, 3, 0),
                ).map { emptyLocation ->
                    world.addEntity(EntityFactory.newFungus(fungusSpread), emptyLocation)
                    fungusSpread.spreadCount++
                }
            true
        } else {
            false
        }
    }
}
