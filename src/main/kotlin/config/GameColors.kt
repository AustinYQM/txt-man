package com.yqmonline.config

import org.hexworks.zircon.api.color.TileColor

object GameColors {
    val WALL_FOREGROUND = TileColor.fromString("#75715E")
    val WALL_BACKGROUND = TileColor.fromString("#3E3D32")

    val FLOOR_FOREGROUND = TileColor.fromString("#75715E")
    val FLOOR_BACKGROUND = TileColor.fromString("#1e2320")

    val ACCENT_COLOR = TileColor.fromString("#FFCD22")

    // Entities
    val FUNGUS_COLOR = TileColor.fromString("#85DD1B")
    val BAT_COLOR = TileColor.fromString("#2348B2")
    val ROCK_COLOR = TileColor.fromString("#DDDDDD")

    // Vision
    val UNREVEALED_COLOR = TileColor.fromString("#090909")
}
