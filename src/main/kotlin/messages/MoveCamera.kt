package com.yqmonline.messages

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.GameMessage
import com.yqmonline.world.GameContext
import org.hexworks.zircon.api.data.Position3D

/**
 * @property context
 * @property source
 * @property previousPosition
 */
data class MoveCamera(
    override val context: GameContext,
    override val source: AnyGameEntity,
    val previousPosition: Position3D,
) : GameMessage
