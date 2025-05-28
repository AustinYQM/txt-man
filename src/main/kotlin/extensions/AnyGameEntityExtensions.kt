package com.yqmonline.extensions

import com.yqmonline.attributes.BlockOccupier
import com.yqmonline.attributes.EntityActions
import com.yqmonline.attributes.EntityPosition
import com.yqmonline.attributes.EntityTile
import com.yqmonline.attributes.VisionBlocker
import com.yqmonline.entities.Player
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KClass

var AnyGameEntity.position
    get() = tryToFindAttribute(EntityPosition::class).position
    set(value) {
        findAttribute(EntityPosition::class).map { it.position = value }
    }

val AnyGameEntity.tile: Tile
    get() = this.tryToFindAttribute(EntityTile::class).tile

fun <T : Attribute> AnyGameEntity.tryToFindAttribute(klass: KClass<T>): T =
    findAttribute(klass).orElseThrow {
        NoSuchElementException("Entity '$this' has no property with type '${klass.simpleName}'.")
    }

val AnyGameEntity.occupiesBlock: Boolean
    get() = findAttribute(BlockOccupier::class).isPresent

val AnyGameEntity.isPlayer: Boolean get() = this.type == Player

suspend fun AnyGameEntity.tryActionsOn(
    context: GameContext,
    target: AnyGameEntity,
): Response {
    var result: Response = Pass
    findAttributeOrNull(EntityActions::class)?.let {
        it.createActionsFor(context, this, target).forEach { action ->
            if (target.receiveMessage(action) is Consumed) {
                result = Consumed
                return@forEach
            }
        }
    }
    return result
}

val AnyGameEntity.blocksVision: Boolean
    get() = this.findAttribute(VisionBlocker::class).isPresent
