package com.yqmonline.extensions

import com.yqmonline.attributes.types.Item
import com.yqmonline.attributes.types.ItemHolder
import com.yqmonline.world.GameContext
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

typealias AnyGameEntity = GameEntity<EntityType>

typealias GameEntity<T> = Entity<T, GameContext>

typealias GameMessage = Message<GameContext>

typealias MessageFacet<T> = BaseFacet<GameContext, T>

typealias GameItem = GameEntity<Item>

typealias GameItemHolder = GameEntity<ItemHolder>
