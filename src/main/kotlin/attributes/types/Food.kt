package com.yqmonline.attributes.types

import com.yqmonline.attributes.NutritionalValue
import com.yqmonline.extensions.GameEntity
import com.yqmonline.extensions.tryToFindAttribute

interface Food : Item

val GameEntity<Food>.energy: Int
    get() = tryToFindAttribute(NutritionalValue::class).energy
