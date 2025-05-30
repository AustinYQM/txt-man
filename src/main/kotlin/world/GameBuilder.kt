package com.yqmonline.world

import com.yqmonline.config.GameConfig.BATS_PER_LEVEL
import com.yqmonline.config.GameConfig.DUNGEON_LEVELS
import com.yqmonline.config.GameConfig.FUNGI_PER_LEVEL
import com.yqmonline.config.GameConfig.LOG_AREA_HEIGHT
import com.yqmonline.config.GameConfig.ROCKS_PER_LEVEL
import com.yqmonline.config.GameConfig.SIDEBAR_WIDTH
import com.yqmonline.config.GameConfig.WINDOW_HEIGHT
import com.yqmonline.config.GameConfig.WINDOW_WIDTH
import com.yqmonline.config.GameConfig.WORLD_SIZE
import com.yqmonline.config.GameConfig.ZOMBIES_PER_LEVEL
import com.yqmonline.entities.EntityFactory
import com.yqmonline.entities.Player
import com.yqmonline.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D

class GameBuilder(
    val worldSize: Size3D,
) {
    private val visibleSize =
        Size3D.create(
            xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
            yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
            zLength = 1,
        )

    val world = WorldBuilder(worldSize).makeCaves().build(visibleSize = visibleSize)

    private fun prepareWorld() = also { world.scrollUpBy(world.actualSize.zLength) }

    fun buildGame(): Game {
        prepareWorld()
        val player = addPlayer()
        addFungi()
        addBats()
        addRocks()

        addZombies()

        world.addWorldEntity(EntityFactory.newFogOfWar())

        return Game.create(
            player = player,
            world = world,
        )
    }

    private fun addZombies() {
        repeat(world.actualSize.zLength) { level ->
            repeat(ZOMBIES_PER_LEVEL) {
                EntityFactory.newZombie().addToWorld(level)
            }
        }
    }

    private fun addPlayer(): GameEntity<Player> =
        EntityFactory
            .newPlayer()
            .addToWorld(
                atLevel = DUNGEON_LEVELS - 1,
                atArea = world.visibleSize.to2DSize(),
            )

    private fun addFungi() =
        also {
            repeat(world.actualSize.zLength) { level ->
                repeat(FUNGI_PER_LEVEL) { EntityFactory.newFungus().addToWorld(level) }
            }
        }

    private fun addBats() =
        also {
            repeat(world.actualSize.zLength) { level ->
                repeat(BATS_PER_LEVEL) {
                    EntityFactory.newBat().addToWorld(level)
                }
            }
        }

    private fun addRocks() =
        also {
            repeat(world.actualSize.zLength) { level ->
                repeat(ROCKS_PER_LEVEL) {
                    EntityFactory.newRock().addToWorld(level)
                }
            }
        }

    private fun <T : EntityType> GameEntity<T>.addToWorld(
        atLevel: Int,
        atArea: Size = world.actualSize.to2DSize(),
    ): GameEntity<T> {
        world.addAtEmptyPosition(
            this,
            offset = Position3D.defaultPosition().withZ(atLevel),
            size = Size3D.from2DSize(atArea),
        )
        return this
    }

    companion object {
        fun create() =
            GameBuilder(
                worldSize = WORLD_SIZE,
            ).buildGame()
    }
}
