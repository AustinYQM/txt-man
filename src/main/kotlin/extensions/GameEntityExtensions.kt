package com.yqmonline.extensions

import com.yqmonline.attributes.types.Combatant
import com.yqmonline.attributes.types.combatStats

fun GameEntity<Combatant>.hasNoHealthLeft(): Boolean = combatStats.hp <= 0
