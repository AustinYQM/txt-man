package com.yqmonline.entities

import com.yqmonline.attributes.EntityPosition
import com.yqmonline.attributes.EntityTile
import com.yqmonline.tiles.GameTileRepository.PLAYER
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit,
) = newEntityOfType(type, init)

object EntityFactory {
    fun newPlayer() =
        newGameEntityOfType(Player) {
            attributes(EntityPosition(), EntityTile(PLAYER))
            behaviors()
            facets()
        }
}
