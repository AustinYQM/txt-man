package com.yqmonline.tiles

import com.yqmonline.config.GameColors
import com.yqmonline.config.GameColors.ACCENT_COLOR
import com.yqmonline.config.GameColors.BAT_COLOR
import com.yqmonline.config.GameColors.BAT_MEAT_COLOR
import com.yqmonline.config.GameColors.FLOOR_BACKGROUND
import com.yqmonline.config.GameColors.FLOOR_FOREGROUND
import com.yqmonline.config.GameColors.FUNGUS_COLOR
import com.yqmonline.config.GameColors.ROCK_COLOR
import com.yqmonline.config.GameColors.UNREVEALED_COLOR
import com.yqmonline.config.GameColors.WALL_BACKGROUND
import com.yqmonline.config.GameColors.WALL_FOREGROUND
import org.hexworks.zircon.api.color.ANSITileColor
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

    val STAIRS_UP =
        Tile
            .newBuilder()
            .withCharacter('<')
            .withForegroundColor(ACCENT_COLOR)
            .withBackgroundColor(FLOOR_BACKGROUND)
            .buildCharacterTile()

    val STAIRS_DOWN =
        Tile
            .newBuilder()
            .withCharacter('>')
            .withForegroundColor(ACCENT_COLOR)
            .withBackgroundColor(FLOOR_BACKGROUND)
            .buildCharacterTile()

    val UNREVEALED =
        Tile
            .newBuilder()
            .withCharacter(' ')
            .withBackgroundColor(UNREVEALED_COLOR)
            .buildCharacterTile()

    val BAT =
        Tile
            .newBuilder()
            .withCharacter('b')
            .withBackgroundColor(FLOOR_BACKGROUND)
            .withForegroundColor(BAT_COLOR)
            .buildCharacterTile()

    val ROCK =
        Tile
            .newBuilder()
            .withCharacter(',')
            .withBackgroundColor(FLOOR_BACKGROUND)
            .withForegroundColor(ROCK_COLOR)
            .buildCharacterTile()

    val BAT_MEAT =
        Tile
            .newBuilder()
            .withCharacter('m')
            .withBackgroundColor(FLOOR_BACKGROUND)
            .withForegroundColor(BAT_MEAT_COLOR)
            .buildCharacterTile()

    val CLUB =
        Tile
            .newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.GRAY)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val DAGGER =
        Tile
            .newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val SWORD =
        Tile
            .newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val STAFF =
        Tile
            .newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.YELLOW)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val JACKET =
        Tile
            .newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.GRAY)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val LIGHT_ARMOR =
        Tile
            .newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.GREEN)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val MEDIUM_ARMOR =
        Tile
            .newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val HEAVY_ARMOR =
        Tile
            .newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()
}
