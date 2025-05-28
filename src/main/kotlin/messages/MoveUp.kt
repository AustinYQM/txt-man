package com.yqmonline.messages

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.GameMessage
import com.yqmonline.world.GameContext

data class MoveUp(
    override val context: GameContext,
    override val source: AnyGameEntity,
) : GameMessage
