package com.yqmonline.blocks

import com.yqmonline.entities.EntityFactory
import com.yqmonline.tiles.GameTileRepository.FLOOR

object GameBlockFactory {
    fun floor() = GameBlock(FLOOR)

    fun wall() = GameBlock.createWith(EntityFactory.newWall())

    fun stairsDown() = GameBlock.createWith(EntityFactory.newStairsDown())

    fun stairsUp() = GameBlock.createWith(EntityFactory.newStairsUp())
}
