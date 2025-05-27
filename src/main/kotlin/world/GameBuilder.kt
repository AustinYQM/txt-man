package com.yqmonline.world

import com.yqmonline.config.GameConfig.DUNGEON_LEVELS
import com.yqmonline.config.GameConfig.LOG_AREA_HEIGHT
import com.yqmonline.config.GameConfig.SIDEBAR_WIDTH
import com.yqmonline.config.GameConfig.WINDOW_HEIGHT
import com.yqmonline.config.GameConfig.WINDOW_WIDTH
import com.yqmonline.config.GameConfig.WORLD_SIZE
import com.yqmonline.entities.EntityFactory
import com.yqmonline.entities.Player
import com.yqmonline.extensions.GameEntity
import org.hexworks.zircon.api.data.Position3D
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

    private fun prepareWorld() =
        also {
            world.scrollUpBy(world.actualSize.zLength)
        }

    private fun addPlayer(): GameEntity<Player> {
        val player = EntityFactory.newPlayer()
        world.addAtEmptyPosition(
            player,
            offset = Position3D.create(0, 0, DUNGEON_LEVELS - 1),
            size = world.visibleSize.copy(zLength = 0),
        )
        return player
    }

    fun buildGame(): Game {
        prepareWorld()
        val player = addPlayer()

        return Game.create(
            player = player,
            world = world,
        )
    }

    companion object {
        fun create() =
            GameBuilder(
                worldSize = WORLD_SIZE,
            ).buildGame()
    }
}
