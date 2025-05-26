package com.yqmonline.view

import com.yqmonline.config.GameConfig
import com.yqmonline.config.GameConfig.LOG_AREA_HEIGHT
import com.yqmonline.config.GameConfig.SIDEBAR_WIDTH
import com.yqmonline.config.GameConfig.WINDOW_HEIGHT
import com.yqmonline.config.GameConfig.WINDOW_WIDTH
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class PlayView(
    private val grid: TileGrid,
) : BaseView(grid, GameConfig.THEME) {
    init {
        val sidebar =
            Components
                .panel()
                .withSize(SIDEBAR_WIDTH, WINDOW_HEIGHT)
                .withDecorations(box())
                .build()

        val logArea =
            Components
                .logArea()
                .withDecorations(box(title = "Log"))
                .withSize(WINDOW_WIDTH - SIDEBAR_WIDTH, LOG_AREA_HEIGHT)
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .build()

        screen.addComponents(sidebar, logArea)
    }
}
