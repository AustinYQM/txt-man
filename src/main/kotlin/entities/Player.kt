package com.yqmonline.entities

import com.yqmonline.attributes.types.Combatant
import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(name = "player"), Combatant
