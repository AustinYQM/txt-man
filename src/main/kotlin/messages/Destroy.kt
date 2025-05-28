package com.yqmonline.messages

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.GameMessage
import com.yqmonline.world.GameContext

data class Destroy(
    override val context: GameContext,
    override val source: AnyGameEntity,
    val target: AnyGameEntity,
    val cause: String = "natural causes.",
) : GameMessage
