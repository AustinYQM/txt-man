package com.yqmonline.messages

import com.yqmonline.attributes.types.EnergyUser
import com.yqmonline.attributes.types.Food
import com.yqmonline.extensions.GameEntity
import com.yqmonline.world.GameContext

data class Eat(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    override val target: GameEntity<Food>,
) : EntityAction<EnergyUser, Food>
