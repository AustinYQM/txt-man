package com.yqmonline.attributes

import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.zircon.api.data.Tile

/** @property tile */
data class EntityTile(
    val tile: Tile = Tile.empty(),
) : BaseAttribute()
