package com.yqmonline.extensions

import com.yqmonline.attributes.BlockOccupier
import com.yqmonline.attributes.CombatStats
import com.yqmonline.attributes.EntityActions
import com.yqmonline.attributes.EntityPosition
import com.yqmonline.attributes.EntityTile
import com.yqmonline.attributes.Equipment
import com.yqmonline.attributes.ItemCombatStats
import com.yqmonline.attributes.VisionBlocker
import com.yqmonline.entities.Player
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.isSuperclassOf

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

inline fun <reified T : EntityType> Iterable<AnyGameEntity>.filterType(): List<AnyGameEntity> =
    filter { T::class.isSuperclassOf(it.type::class) }.toList()

inline fun <reified T : EntityType> AnyGameEntity.whenTypeIs(fn: (GameEntity<T>) -> Unit) {
    if (this.type::class.isSubclassOf(T::class)) {
        fn(this as GameEntity<T>)
    }
}

val AnyGameEntity.attackValue: Int
    get() {
        val combat = findAttribute(CombatStats::class).map { it.attackValue }.orElse(0)
        val equipment = findAttribute(Equipment::class).map { it.attackValue }.orElse(0)
        val item = findAttribute(ItemCombatStats::class).map { it.attackValue }.orElse(0)
        return combat + equipment + item
    }

val AnyGameEntity.defenseValue: Int
    get() {
        val combat = findAttribute(CombatStats::class).map { it.defenseValue }.orElse(0)
        val equipment = findAttribute(Equipment::class).map { it.defenseValue }.orElse(0)
        val item = findAttribute(ItemCombatStats::class).map { it.defenseValue }.orElse(0)
        return combat + equipment + item
    }
