package com.yqmonline.entities

import com.yqmonline.attributes.types.Combatant
import com.yqmonline.attributes.types.EnergyUser
import com.yqmonline.attributes.types.EquipmentHolder
import com.yqmonline.attributes.types.ExperienceGainer
import com.yqmonline.attributes.types.ItemHolder
import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(name = "player"), Combatant, ItemHolder, EnergyUser, EquipmentHolder, ExperienceGainer
