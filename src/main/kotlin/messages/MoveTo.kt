package com.yqmonline.messages

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.GameMessage
import com.yqmonline.world.GameContext
import org.hexworks.zircon.api.data.Position3D

data class MoveTo(
    override val context: GameContext,
    override val source: AnyGameEntity,
    val position: Position3D,
) : GameMessage
