package com.yqmonline.messages

import com.yqmonline.attributes.types.EnergyUser
import com.yqmonline.extensions.GameEntity
import com.yqmonline.extensions.GameMessage
import com.yqmonline.world.GameContext

data class Expend(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    val energy: Int,
) : GameMessage
