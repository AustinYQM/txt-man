package com.yqmonline.attributes.types

import com.yqmonline.attributes.CombatStats
import com.yqmonline.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface Combatant : EntityType

val GameEntity<Combatant>.combatStats: CombatStats
    get() = findAttribute(CombatStats::class).get()
