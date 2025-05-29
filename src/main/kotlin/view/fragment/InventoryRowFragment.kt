package com.yqmonline.view.fragment

import com.yqmonline.attributes.types.iconTile
import com.yqmonline.extensions.GameItem
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.graphics.Symbols

class InventoryRowFragment(
    width: Int,
    item: GameItem,
) : Fragment {
    val dropButton =
        Components
            .button()
            .withText("${Symbols.ARROW_DOWN}")
            .withDecorations()
            .build()

    override val root =
        Components
            .hbox()
            .withSpacing(1)
            .withSize(width, 1)
            .build()
            .apply {
                addComponent(
                    Components
                        .icon()
                        .withIcon(item.iconTile)
                        .build(),
                )
                addComponent(
                    Components
                        .label()
                        .withSize(InventoryFragment.NAME_COLUMN_WIDTH, 1)
                        .withText(item.name)
                        .build(),
                )
                addComponent(dropButton)
            }
}
