package com.yqmonline.attributes.types

import com.yqmonline.attributes.CombatStats
import com.yqmonline.attributes.Experience
import com.yqmonline.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface ExperienceGainer : EntityType

val GameEntity<ExperienceGainer>.experience: Experience
    get() = findAttribute(Experience::class).get()

val GameEntity<ExperienceGainer>.combatStats: CombatStats
    get() = findAttribute(CombatStats::class).get()
