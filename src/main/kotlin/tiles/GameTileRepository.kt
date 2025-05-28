package com.yqmonline.tiles

import com.yqmonline.config.GameColors.ACCENT_COLOR
import com.yqmonline.config.GameColors.FLOOR_BACKGROUND
import com.yqmonline.config.GameColors.FLOOR_FOREGROUND
import com.yqmonline.config.GameColors.FUNGUS_COLOR
import com.yqmonline.config.GameColors.WALL_BACKGROUND
import com.yqmonline.config.GameColors.WALL_FOREGROUND
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {
    val EMPTY = Tile.empty()

    val FLOOR: CharacterTile =
        Tile
            .newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(FLOOR_FOREGROUND)
            .withBackgroundColor(FLOOR_BACKGROUND)
            .buildCharacterTile()

    val WALL: CharacterTile =
        Tile
            .newBuilder()
            .withCharacter('#')
            .withForegroundColor(WALL_FOREGROUND)
            .withBackgroundColor(WALL_BACKGROUND)
            .buildCharacterTile()

    val PLAYER =
        Tile
            .newBuilder()
            .withCharacter('@')
            .withBackgroundColor(FLOOR_BACKGROUND)
            .withForegroundColor(ACCENT_COLOR)
            .buildCharacterTile()

    val FUNGUS =
        Tile
            .newBuilder()
            .withCharacter('f')
            .withBackgroundColor(FLOOR_BACKGROUND)
            .withForegroundColor(FUNGUS_COLOR)
            .buildCharacterTile()
}
