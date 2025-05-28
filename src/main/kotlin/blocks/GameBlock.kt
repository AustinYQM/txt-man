package com.yqmonline.blocks

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.occupiesBlock
import com.yqmonline.extensions.tile
import com.yqmonline.tiles.GameTileRepository
import com.yqmonline.tiles.GameTileRepository.EMPTY
import com.yqmonline.tiles.GameTileRepository.FLOOR
import com.yqmonline.tiles.GameTileRepository.PLAYER
import com.yqmonline.tiles.GameTileRepository.WALL
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(
    private var defaultTile: Tile = FLOOR,
    private val currentEntities: MutableList<AnyGameEntity> = mutableListOf(),
) : BaseBlock<Tile>(emptyTile = EMPTY, tiles = persistentMapOf(BlockTileType.CONTENT to defaultTile)) {
    init {
        top = GameTileRepository.UNREVEALED
        updateContent()
    }

    val isFloor: Boolean
        get() = defaultTile == FLOOR

    val isWall: Boolean
        get() = defaultTile == WALL

    val isEmptyFloor: Boolean
        get() = currentEntities.isEmpty()

    val entities: Iterable<AnyGameEntity>
        get() = currentEntities.toList()

    fun addEntity(entity: AnyGameEntity) {
        currentEntities.add(entity)
        updateContent()
    }

    val occupier: Maybe<AnyGameEntity>
        get() = Maybe.ofNullable(currentEntities.firstOrNull { it.occupiesBlock })

    val isOccupied: Boolean
        get() = occupier.isPresent

    fun removeEntity(entity: AnyGameEntity) {
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

    companion object {
        fun createWith(entity: AnyGameEntity) = GameBlock(currentEntities = mutableListOf(entity))
    }
}
