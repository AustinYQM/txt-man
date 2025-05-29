package com.yqmonline.entities

import com.yqmonline.attributes.types.Combatant
import com.yqmonline.attributes.types.ItemHolder
import org.hexworks.amethyst.api.base.BaseEntityType

object Bat :
    BaseEntityType(
        name = "Bat",
    ),
    Combatant,
    ItemHolder
