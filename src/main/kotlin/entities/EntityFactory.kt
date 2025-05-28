package com.yqmonline.entities

import com.yqmonline.attributes.BlockOccupier
import com.yqmonline.attributes.CombatStats
import com.yqmonline.attributes.EntityActions
import com.yqmonline.attributes.EntityPosition
import com.yqmonline.attributes.EntityTile
import com.yqmonline.attributes.FungusSpread
import com.yqmonline.attributes.Vision
import com.yqmonline.attributes.VisionBlocker
import com.yqmonline.messages.Attack
import com.yqmonline.messages.Dig
import com.yqmonline.systems.Attackable
import com.yqmonline.systems.CameraMover
import com.yqmonline.systems.Destructible
import com.yqmonline.systems.Diggable
import com.yqmonline.systems.FogOfWar
import com.yqmonline.systems.FungusGrowth
import com.yqmonline.systems.InputReceiver
import com.yqmonline.systems.Movable
import com.yqmonline.systems.StairClimber
import com.yqmonline.systems.StairDescender
import com.yqmonline.tiles.GameTileRepository.FUNGUS
import com.yqmonline.tiles.GameTileRepository.PLAYER
import com.yqmonline.tiles.GameTileRepository.STAIRS_DOWN
import com.yqmonline.tiles.GameTileRepository.STAIRS_UP
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
                CombatStats.create(maxHp = 100, attackValue = 10, defenseValue = 5),
                Vision(9),
            )
            behaviors(InputReceiver)
            facets(Movable, CameraMover, StairClimber, StairDescender)
        }

    fun newWall() =
        newGameEntityOfType(Wall) {
            attributes(
                EntityPosition(),
                BlockOccupier,
                EntityTile(WALL),
                VisionBlocker,
            )
            facets(Diggable)
        }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) =
        newGameEntityOfType(Fungus) {
            attributes(
                BlockOccupier,
                EntityPosition(),
                EntityTile(FUNGUS),
                fungusSpread,
                CombatStats.create(
                    maxHp = 10,
                    attackValue = 0,
                    defenseValue = 0,
                ),
            )
            facets(Attackable, Destructible)
            behaviors(FungusGrowth)
        }

    fun newStairsDown() =
        newGameEntityOfType(StairsDown) {
            attributes(
                EntityPosition(),
                EntityTile(STAIRS_DOWN),
            )
        }

    fun newStairsUp() =
        newGameEntityOfType(StairsUp) {
            attributes(
                EntityPosition(),
                EntityTile(STAIRS_UP),
            )
        }

    fun newFogOfWar() =
        newGameEntityOfType(FOW) {
            behaviors(FogOfWar)
        }
}
