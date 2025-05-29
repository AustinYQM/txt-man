package com.yqmonline.attributes

import com.yqmonline.extensions.GameItem
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class Inventory(
    val size: Int,
) : BaseAttribute() {
    private val currentItems = mutableListOf<GameItem>()

    val items: List<GameItem>
        get() = currentItems.toList()

    val isEmpty: Boolean
        get() = currentItems.isEmpty()

    val isFull: Boolean
        get() = currentItems.size >= size

    fun findItemBy(id: UUID): Maybe<GameItem> = Maybe.ofNullable(items.firstOrNull { it.id == id })

    fun addItem(item: GameItem): Boolean =
        if (isFull.not()) {
            currentItems.add(item)
        } else {
            false
        }

    fun removeItem(entity: GameItem): Boolean = currentItems.remove(entity)
}
