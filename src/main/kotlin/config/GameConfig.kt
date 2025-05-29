package com.yqmonline.config

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.data.Size3D

object GameConfig {
    // TODO: Move this to config yaml

    const val DUNGEON_LEVELS = 2

    val TILESET = CP437TilesetResources.rogueYun16x16()
    val THEME = ColorThemes.zenburnVanilla()
    const val SIDEBAR_WIDTH = 18
    const val LOG_AREA_HEIGHT = 8

    const val WINDOW_WIDTH = 80
    const val WINDOW_HEIGHT = 50

    const val FUNGI_PER_LEVEL = 15
    const val MAXIMUM_FUNGUS_SPREAD = 20
    const val BATS_PER_LEVEL = 10
    const val ROCKS_PER_LEVEL = 20

    val WORLD_SIZE = Size3D.create(WINDOW_WIDTH * 2, WINDOW_HEIGHT * 2, DUNGEON_LEVELS)
    val GAME_AREA_SIZE =
        Size3D.create(
            xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
            yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
            zLength = DUNGEON_LEVELS,
        )

    fun buildAppConfig() =
        AppConfigBuilder
            .newBuilder()
            .withDefaultTileset(TILESET)
            .withSize(WINDOW_WIDTH, WINDOW_HEIGHT)
            .build()
}
