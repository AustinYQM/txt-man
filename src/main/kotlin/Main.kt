package com.yqmonline

import com.yqmonline.config.GameConfig
import com.yqmonline.view.StartView
import org.hexworks.zircon.api.SwingApplications

fun main(args: Array<String>) {
    val grid = SwingApplications.startTileGrid(GameConfig.buildAppConfig())

    StartView(grid).dock()
}
