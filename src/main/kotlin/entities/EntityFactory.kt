package com.yqmonline.entities

import com.yqmonline.attributes.BlockOccupier
import com.yqmonline.attributes.CombatStats
import com.yqmonline.attributes.EnergyLevel
import com.yqmonline.attributes.EntityActions
import com.yqmonline.attributes.EntityPosition
import com.yqmonline.attributes.EntityTile
import com.yqmonline.attributes.FungusSpread
import com.yqmonline.attributes.Inventory
import com.yqmonline.attributes.ItemIcon
import com.yqmonline.attributes.NutritionalValue
import com.yqmonline.attributes.Vision
import com.yqmonline.attributes.VisionBlocker
import com.yqmonline.entities.items.BatMeat
import com.yqmonline.entities.items.Rock
import com.yqmonline.entities.terrain.StairsDown
import com.yqmonline.entities.terrain.StairsUp
import com.yqmonline.entities.terrain.Wall
import com.yqmonline.messages.Attack
import com.yqmonline.messages.Dig
import com.yqmonline.systems.Attackable
import com.yqmonline.systems.CameraMover
import com.yqmonline.systems.Destructible
import com.yqmonline.systems.DigestiveSystem
import com.yqmonline.systems.Diggable
import com.yqmonline.systems.EnergyExpender
import com.yqmonline.systems.FogOfWar
import com.yqmonline.systems.FungusGrowth
import com.yqmonline.systems.InputReceiver
import com.yqmonline.systems.InventoryInspector
import com.yqmonline.systems.ItemDropper
import com.yqmonline.systems.ItemPicker
import com.yqmonline.systems.LootDropper
import com.yqmonline.systems.Movable
import com.yqmonline.systems.StairClimber
import com.yqmonline.systems.StairDescender
import com.yqmonline.systems.Wanderer
import com.yqmonline.tiles.GameTileRepository.BAT
import com.yqmonline.tiles.GameTileRepository.BAT_MEAT
import com.yqmonline.tiles.GameTileRepository.FUNGUS
import com.yqmonline.tiles.GameTileRepository.PLAYER
import com.yqmonline.tiles.GameTileRepository.ROCK
import com.yqmonline.tiles.GameTileRepository.STAIRS_DOWN
import com.yqmonline.tiles.GameTileRepository.STAIRS_UP
import com.yqmonline.tiles.GameTileRepository.WALL
import com.yqmonline.utils.loggerFor
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.data.Tile
import kotlin.random.Random

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit,
) = newEntityOfType(type, init)

object EntityFactory {
    private val LOG = loggerFor<EntityFactory>()

    fun newPlayer() =
        newGameEntityOfType(Player) {
            attributes(
                EntityPosition(),
                BlockOccupier,
                EntityTile(PLAYER),
                EntityActions(Dig::class, Attack::class),
                CombatStats.create(maxHp = 100, attackValue = 10, defenseValue = 5),
                Vision(9),
                Inventory(10),
                EnergyLevel(1000, 1000),
            )
            behaviors(InputReceiver, EnergyExpender)
            facets(
                Movable,
                CameraMover,
                StairClimber,
                StairDescender,
                Attackable,
                Destructible,
                ItemPicker,
                InventoryInspector,
                ItemDropper,
                EnergyExpender,
                DigestiveSystem,
            )
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

    fun newBat() =
        newGameEntityOfType(Bat) {
            val meat: Int = Random.nextInt(1, 3)
            attributes(
                Inventory(meat).apply { for (i in 0..meat) addItem(newBatMeat()) },
                BlockOccupier,
                EntityPosition(),
                EntityTile(BAT),
                CombatStats.create(
                    maxHp = 5,
                    attackValue = 2,
                    defenseValue = 1,
                ),
                EntityActions(Attack::class),
            )
            facets(Movable, Attackable, LootDropper, ItemDropper, Destructible)
            behaviors(Wanderer)
        }

    fun newRock() =
        newGameEntityOfType(Rock) {
            attributes(
                ItemIcon(
                    Tile
                        .newBuilder()
                        .withName("white gem")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile(),
                ),
                EntityPosition(),
                EntityTile(ROCK),
            )
        }

    fun newBatMeat() =
        newGameEntityOfType(BatMeat) {
            attributes(
                ItemIcon(
                    Tile
                        .newBuilder()
                        .withName("Meatball")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile(),
                ),
                NutritionalValue(750),
                EntityPosition(),
                EntityTile(BAT_MEAT),
            )
        }
}
