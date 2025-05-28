package com.yqmonline.entities

import com.yqmonline.attributes.BlockOccupier
import com.yqmonline.attributes.EntityActions
import com.yqmonline.attributes.EntityPosition
import com.yqmonline.attributes.EntityTile
import com.yqmonline.attributes.FungusSpread
import com.yqmonline.messages.Attack
import com.yqmonline.messages.Dig
import com.yqmonline.systems.Attackable
import com.yqmonline.systems.CameraMover
import com.yqmonline.systems.Diggable
import com.yqmonline.systems.FungusGrowth
import com.yqmonline.systems.InputReceiver
import com.yqmonline.tiles.GameTileRepository.FUNGUS
import com.yqmonline.tiles.GameTileRepository.PLAYER
import com.yqmonline.tiles.GameTileRepository.WALL
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
            attributes(
                EntityPosition(),
                EntityTile(PLAYER),
                EntityActions(Dig::class, Attack::class),
            )
            behaviors(InputReceiver)
            facets(Movable, CameraMover)
        }

    fun newWall() =
        newGameEntityOfType(Wall) {
            attributes(
                EntityPosition(),
                BlockOccupier,
                EntityTile(WALL),
            )
            facets(Diggable)
        }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) =
        newGameEntityOfType(Fungus) {
            attributes(BlockOccupier, EntityPosition(), EntityTile(FUNGUS), fungusSpread)
            facets(Attackable)
            behaviors(FungusGrowth)
        }
}
