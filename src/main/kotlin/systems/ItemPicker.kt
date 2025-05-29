package com.yqmonline.systems

import com.yqmonline.attributes.types.Item
import com.yqmonline.attributes.types.addItem
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.GameItem
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.filterType
import com.yqmonline.extensions.isPlayer
import com.yqmonline.messages.PickItemUp
import com.yqmonline.world.World
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Position3D

object ItemPicker : MessageFacet<PickItemUp>(PickItemUp::class) {
    override suspend fun receive(message: PickItemUp): Response {
        val (context, itemHolder, position) = message
        val world = context.world

        world.findTopItem(position).map { item ->
            if (itemHolder.addItem(item)) {
                world.removeEntity(item)
                val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
                val verb = if (itemHolder.isPlayer) "pick up" else "picks up"
                logGameEvent("$subject $verb the $item", ItemPicker)
            }
        }
        return Consumed
    }

    private fun World.findTopItem(position: Position3D): Maybe<GameItem> =
        fetchBlockAt(position).flatMap { block ->
            Maybe.ofNullable(block.entities.filterType<Item>().firstOrNull() as GameItem?)
        }
}
