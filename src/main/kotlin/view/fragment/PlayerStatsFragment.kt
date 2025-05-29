package com.yqmonline.view.fragment

import com.yqmonline.attributes.DisplayableAttribute
import com.yqmonline.entities.Player
import com.yqmonline.extensions.GameEntity
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment

class PlayerStatsFragment(
    width: Int,
    player: GameEntity<Player>,
) : Fragment {
    /**
     * The [Component] this [Fragment] contains.
     */
    override val root =
        Components
            .vbox()
            .withSize(width, 30)
            .withSpacing(1)
            .build()
            .apply {
                addComponent(Components.header().withText("Player"))
                player.attributes
                    .toList()
                    .filterIsInstance<DisplayableAttribute>()
                    .forEach {
                        addComponent(it.toComponent(width))
                    }
            }
}
