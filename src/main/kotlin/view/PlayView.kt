package com.yqmonline.view

import com.yqmonline.config.GameConfig
import com.yqmonline.config.GameConfig.LOG_AREA_HEIGHT
import com.yqmonline.config.GameConfig.SIDEBAR_WIDTH
import com.yqmonline.config.GameConfig.WINDOW_HEIGHT
import com.yqmonline.config.GameConfig.WINDOW_WIDTH
import com.yqmonline.config.GameTileRepository.FLOOR
import com.yqmonline.events.GameLogEvent
import com.yqmonline.events.PlayerGainedLevel
import com.yqmonline.view.fragment.PlayerStatsFragment
import com.yqmonline.view.fragment.dialog.LevelUpDialog
import com.yqmonline.world.Game
import com.yqmonline.world.GameBuilder
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer

/**
 * @param grid
 * @param game
 * @param theme
 */
class PlayView(
    private val grid: TileGrid,
    private val game: Game = GameBuilder.create(),
    theme: ColorTheme = GameConfig.THEME,
) : BaseView(grid, theme) {
    init {
        val sidebar =
            Components
                .panel()
                .withSize(SIDEBAR_WIDTH, WINDOW_HEIGHT)
                .withDecorations(box())
                .build()

        sidebar.addFragment(
            PlayerStatsFragment(
                width = sidebar.contentSize.width,
                player = game.player,
            ),
        )

        val logArea =
            Components
                .logArea()
                .withDecorations(box(title = "Log"))
                .withSize(WINDOW_WIDTH - SIDEBAR_WIDTH, LOG_AREA_HEIGHT)
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .build()

        val gameComponent =
            Components
                .panel()
                .withSize(game.world.visibleSize.to2DSize())
                .withComponentRenderer(
                    GameAreaComponentRenderer(
                        gameArea = game.world,
                        projectionMode = ProjectionMode.TOP_DOWN.toProperty(),
                        fillerTile = FLOOR,
                    ),
                ).withAlignmentWithin(screen, ComponentAlignment.TOP_RIGHT)
                .build()

        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
            game.world.update(screen, event, game)
            Processed
        }

        screen.addComponents(sidebar, logArea, gameComponent)

        Zircon.eventBus.subscribeTo<GameLogEvent> { (text) ->
            logArea.addParagraph(
                paragraph = text,
                withNewLine = false,
                withTypingEffectSpeedInMs = 10,
            )
            KeepSubscription
        }

        Zircon.eventBus.subscribeTo<PlayerGainedLevel> {
            screen.openModal(LevelUpDialog(screen, game.player))
            KeepSubscription
        }

        game.world.update(
            screen,
            KeyboardEvent(
                type = KeyboardEventType.KEY_TYPED,
                key = "",
                code = KeyCode.DEAD_GRAVE,
            ),
            game,
        )
    }
}
