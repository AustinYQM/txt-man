package com.yqmonline.attributes.types

import com.yqmonline.attributes.ItemCombatStats
import com.yqmonline.extensions.GameEntity

interface Armor : CombatItem

val GameEntity<Armor>.attackValue: Int
    get() = findAttribute(ItemCombatStats::class).get().attackValue

val GameEntity<Armor>.defenseValue: Int
    get() = findAttribute(ItemCombatStats::class).get().defenseValue
