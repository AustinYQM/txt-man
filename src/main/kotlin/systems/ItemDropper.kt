package com.yqmonline.systems

import com.yqmonline.attributes.types.removeItem
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.isPlayer
import com.yqmonline.messages.DropItem
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response

object ItemDropper : MessageFacet<DropItem>(DropItem::class) {
    override suspend fun receive(message: DropItem): Response {
        val (context, itemHolder, item, position) = message

        if (itemHolder.removeItem(item)) {
            context.world.addEntity(item, position)
            val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
            val verb = if (itemHolder.isPlayer) "drop" else "drops"
            logGameEvent("$subject $verb the $item", ItemDropper)
        }
        return Consumed
    }
}
