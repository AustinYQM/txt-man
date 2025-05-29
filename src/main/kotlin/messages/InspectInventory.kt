package com.yqmonline.messages

import com.yqmonline.extensions.GameItemHolder
import com.yqmonline.extensions.GameMessage
import com.yqmonline.world.GameContext
import org.hexworks.zircon.api.data.Position3D

data class InspectInventory(
    override val context: GameContext,
    override val source: GameItemHolder,
    val position: Position3D,
) : GameMessage
