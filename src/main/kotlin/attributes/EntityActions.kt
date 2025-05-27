package com.yqmonline.attributes

import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.messages.EntityAction
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.reflect.KClass

class EntityActions(
    private vararg val actions: KClass<out EntityAction<out EntityType, out EntityType>>,
) : BaseAttribute() {
    fun createActionsFor(
        context: GameContext,
        source: AnyGameEntity,
        target: AnyGameEntity,
    ): Iterable<EntityAction<out EntityType, out EntityType>> =
        actions.map {
            try {
                it.constructors.first().call(context, source, target)
            } catch (e: Exception) {
                throw IllegalArgumentException("Can't create EntityAction. Does it have the proper constructor?")
            }
        }
}
