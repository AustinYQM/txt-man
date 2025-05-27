package com.yqmonline.world

import com.yqmonline.entities.Player
import com.yqmonline.extensions.GameEntity

class Game(
    val world: World,
    val player: GameEntity<Player>,
) {
    companion object {
        fun create(
            player: GameEntity<Player>,
            world: World,
        ) = Game(
            world = world,
            player = player,
        )
    }
}
