package com.yqmonline.entities.items

import com.yqmonline.attributes.types.Item
import kotlinx.serialization.Serializable
import org.hexworks.amethyst.api.base.BaseEntityType

object Rock :
    BaseEntityType(
        name = "Rock",
        description = "A small rock. Probably doesn't taste good.",
    ),
    Item
