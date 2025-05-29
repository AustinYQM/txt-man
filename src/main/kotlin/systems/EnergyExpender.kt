package com.yqmonline.systems

import com.yqmonline.attributes.EnergyLevel
import com.yqmonline.attributes.types.EnergyUser
import com.yqmonline.attributes.types.energyLevel
import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.whenTypeIs
import com.yqmonline.messages.Destroy
import com.yqmonline.messages.Expend
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseActor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

object EnergyExpender : BaseActor<GameContext, Expend>(Expend::class, EnergyLevel::class) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override val messageType: KClass<Expend> = Expend::class

    override suspend fun update(
        entity: Entity<EntityType, GameContext>,
        context: GameContext,
    ): Boolean {
        entity.whenTypeIs<EnergyUser> {
            entity.receiveMessage(
                Expend(
                    context = context,
                    source = it,
                    energy = 2,
                ),
            )
        }
        return true
    }

    override suspend fun receive(message: Expend): Response {
        val (context, entity, energy) = message
        entity.energyLevel.currentEnergy -= energy
        checkStarvation(context, entity, entity.energyLevel)
        return Consumed
    }

    private suspend fun checkStarvation(
        context: GameContext,
        entity: AnyGameEntity,
        energyLevel: EnergyLevel,
    ) {
        if (energyLevel.currentEnergy <= 0) {
            entity.receiveMessage(
                Destroy(
                    context = context,
                    source = entity,
                    target = entity,
                    cause = "because of starvation",
                ),
            )
        }
    }
}
