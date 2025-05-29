package com.yqmonline.entities.items

import com.yqmonline.attributes.types.Food
import org.hexworks.amethyst.api.base.BaseEntityType

object BatMeat :
    BaseEntityType(
        name = "Bat Meat",
        description = "It's bat meat. From a bat. A dead one... hopefully.",
    ),
    Food
