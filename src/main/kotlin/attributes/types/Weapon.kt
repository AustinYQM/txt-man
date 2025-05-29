package com.yqmonline.attributes.types

import com.yqmonline.attributes.ItemCombatStats
import com.yqmonline.extensions.GameEntity

interface Weapon : CombatItem

val GameEntity<Weapon>.attackValue: Int
    get() = findAttribute(ItemCombatStats::class).get().attackValue

val GameEntity<Weapon>.defenseValue: Int
    get() = findAttribute(ItemCombatStats::class).get().defenseValue
