package com.yqmonline.view

import com.yqmonline.config.GameConfig
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class StartView(
    private val grid: TileGrid,
) : BaseView(grid, GameConfig.THEME) {
    init {
        val msg = "Welcome to TXT Man!"

        val header =
            Components
                .textBox(contentWidth = msg.length)
                .addHeader(msg)
                .addNewLine()
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build()

        val startButton =
            Components
                .button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_CENTER)
                .withText("Start!")
                .withDecorations(box(), shadow())
                .build()

        startButton.onActivated {
            replaceWith(PlayView(grid))
        }

        screen.addComponents(header, startButton)
    }
}
