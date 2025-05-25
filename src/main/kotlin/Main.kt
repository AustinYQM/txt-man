package com.yqmonline

import com.yqmonline.view.StartView
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig

fun main(args: Array<String>) {
    val grid =
        SwingApplications.startTileGrid(
            AppConfig
                .newBuilder()
                .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
                .build(),
        )

    StartView(grid).dock()
}
