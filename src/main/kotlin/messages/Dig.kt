package com.yqmonline.messages

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Dig(
    override val context: GameContext,
    override val source: AnyGameEntity,
    override val target: AnyGameEntity,
) : EntityAction<EntityType, EntityType>
