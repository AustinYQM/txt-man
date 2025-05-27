package com.yqmonline.messages

import com.yqmonline.extensions.GameEntity
import com.yqmonline.extensions.GameMessage
import org.hexworks.amethyst.api.entity.EntityType

interface EntityAction<S : EntityType, T : EntityType> : GameMessage {
    val target: GameEntity<T>

    operator fun component1() = context

    operator fun component2() = source

    operator fun component3() = target
}
