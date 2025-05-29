package com.yqmonline.systems

import com.yqmonline.attributes.types.ItemHolder
import com.yqmonline.attributes.types.inventory
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.position
import com.yqmonline.extensions.whenTypeIs
import com.yqmonline.messages.Destroy
import com.yqmonline.messages.DropItem
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response

object LootDropper : MessageFacet<Destroy>(Destroy::class) {
    /**
     * Receives the given [message].
     * @see Response
     */
    override suspend fun receive(message: Destroy): Response {
        val (context, _, target) = message
        target.whenTypeIs<ItemHolder> { entity ->
            entity.inventory.items.forEach { item ->
                entity.receiveMessage(
                    DropItem(
                        context = context,
                        source = entity,
                        item = item,
                        position = entity.position,
                    ),
                )
            }
        }
        return Pass
    }
}
