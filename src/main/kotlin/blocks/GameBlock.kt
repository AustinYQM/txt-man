package com.yqmonline.blocks

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext
import com.yqmonline.tiles.GameTileRepository.EMPTY
import com.yqmonline.tiles.GameTileRepository.FLOOR
import com.yqmonline.tiles.GameTileRepository.WALL
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock
import javax.swing.text.AbstractDocument

class GameBlock(content: Tile = FLOOR) : BaseBlock<Tile>(emptyTile = EMPTY, tiles = persistentMapOf(BlockTileType.CONTENT to content)) {
    
    val isFloor: Boolean get() = content == FLOOR
    
    val isWall: Boolean get() = content == WALL
    
}