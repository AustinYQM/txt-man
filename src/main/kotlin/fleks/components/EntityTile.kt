package com.yqmonline.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import org.hexworks.zircon.api.data.Tile

/**
 * A Component that holds a drawable [Tile]. This is a tile that should appear in world.
 *
 * @param tile A tile to be drawn on screen. Defaults to an empty tile if none is provided.
 * @see Tile
 */
data class EntityTile(
    val tile: Tile = Tile.empty(),
) : Component<EntityTile> {
    /**
     * @return the [ComponentType] of this [Component]
     */
    override fun type() = EntityTile

    /**
     * The companion object of the [EntityTile] component.
     * This stores the unique id of the [EntityTile] component
     * allowing it to be grabbed directly O(1) with the id and to
     * help with recycling of entities and components.
     */
    companion object : ComponentType<EntityTile>()
}
