package com.yqmonline.view

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class PlayView(
    private val grid: TileGrid,
) : BaseView(grid, ColorThemes.arc()) {
    init {
        val loseButton =
            Components
                .button()
                .withAlignmentWithin(screen, ComponentAlignment.LEFT_CENTER)
                .withText("Lose!")
                .withDecorations(box(), shadow())
                .build()

        val winButton =
            Components
                .button()
                .withAlignmentWithin(screen, ComponentAlignment.RIGHT_CENTER)
                .withText("Win!")
                .withDecorations(box(), shadow())
                .build()

        winButton.onActivated { replaceWith(WinView(grid)) }
        loseButton.onActivated { replaceWith(LoseView(grid)) }

        screen.addComponents(loseButton, winButton)
    }
}
