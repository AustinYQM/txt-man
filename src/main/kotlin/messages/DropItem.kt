package com.yqmonline.messages

import com.yqmonline.extensions.GameItem
import com.yqmonline.extensions.GameItemHolder
import com.yqmonline.extensions.GameMessage
import com.yqmonline.world.GameContext
import org.hexworks.zircon.api.data.Position3D

data class DropItem(
    override val context: GameContext,
    override val source: GameItemHolder,
    val item: GameItem,
    val position: Position3D,
) : GameMessage
