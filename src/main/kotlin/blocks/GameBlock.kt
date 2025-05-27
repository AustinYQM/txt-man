package com.yqmonline.blocks

import com.yqmonline.extensions.GameEntity
import com.yqmonline.extensions.tile
import com.yqmonline.tiles.GameTileRepository.EMPTY
import com.yqmonline.tiles.GameTileRepository.FLOOR
import com.yqmonline.tiles.GameTileRepository.PLAYER
import com.yqmonline.tiles.GameTileRepository.WALL
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(
    private var defaultTile: Tile = FLOOR,
    private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf(),
) : BaseBlock<Tile>(emptyTile = EMPTY, tiles = persistentMapOf(BlockTileType.CONTENT to defaultTile)) {
    val isFloor: Boolean
        get() = defaultTile == FLOOR

    val isWall: Boolean
        get() = defaultTile == WALL

    val isEmptyFloor: Boolean
        get() = currentEntities.isEmpty()

    val entities: Iterable<GameEntity<EntityType>>
        get() = currentEntities.toList()

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
        updateContent()
    }

    fun removeEntity(entity: GameEntity<EntityType>) {
        currentEntities.remove(entity)
        updateContent()
    }

    private fun updateContent() {
        val entityTiles = currentEntities.map { it.tile }
        content =
            when {
                entityTiles.contains(PLAYER) -> PLAYER
                entityTiles.isNotEmpty() -> entityTiles.first()
                else -> defaultTile
            }
    }
}
