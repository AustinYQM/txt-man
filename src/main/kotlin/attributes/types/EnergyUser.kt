package com.yqmonline.attributes.types

import com.yqmonline.attributes.EnergyLevel
import com.yqmonline.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface EnergyUser : EntityType

val GameEntity<EnergyUser>.energyLevel: EnergyLevel
    get() = findAttribute(EnergyLevel::class).get()
