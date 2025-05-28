package com.yqmonline.messages

import com.yqmonline.attributes.types.Combatant
import com.yqmonline.extensions.GameEntity
import com.yqmonline.world.GameContext

/**
 * @property context
 * @property source
 * @property target
 */
data class Attack(
    override val context: GameContext,
    override val source: GameEntity<Combatant>,
    override val target: GameEntity<Combatant>,
) : EntityAction<Combatant, Combatant>
