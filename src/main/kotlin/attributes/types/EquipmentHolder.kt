package com.yqmonline.attributes.types

import com.yqmonline.attributes.Equipment
import com.yqmonline.attributes.Inventory
import com.yqmonline.extensions.GameCombatItem
import com.yqmonline.extensions.GameEquipmentHolder
import org.hexworks.amethyst.api.entity.EntityType

interface EquipmentHolder : EntityType

fun GameEquipmentHolder.equip(
    inventory: Inventory,
    item: GameCombatItem,
): GameCombatItem = equipment.equip(inventory, item)

val GameEquipmentHolder.equipment: Equipment
    get() = findAttribute(Equipment::class).get()
