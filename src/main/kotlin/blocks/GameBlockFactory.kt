package com.yqmonline.blocks

import com.yqmonline.tiles.GameTileRepository.FLOOR
import com.yqmonline.tiles.GameTileRepository.WALL

object GameBlockFactory {
    
    fun floor() = GameBlock(FLOOR)
    fun wall() = GameBlock(WALL)
}