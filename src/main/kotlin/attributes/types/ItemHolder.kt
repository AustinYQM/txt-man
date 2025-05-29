package com.yqmonline.attributes.types

import com.yqmonline.attributes.Inventory
import com.yqmonline.extensions.GameItem
import com.yqmonline.extensions.GameItemHolder
import org.hexworks.amethyst.api.entity.EntityType

interface ItemHolder : EntityType

fun GameItemHolder.addItem(item: GameItem) = inventory.addItem(item)

fun GameItemHolder.removeItem(item: GameItem) = inventory.removeItem(item)

val GameItemHolder.inventory: Inventory
    get() = findAttribute(Inventory::class).get()
