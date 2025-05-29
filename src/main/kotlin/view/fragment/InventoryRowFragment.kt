package com.yqmonline.view.fragment

import com.yqmonline.attributes.types.Food
import com.yqmonline.attributes.types.iconTile
import com.yqmonline.extensions.GameItem
import com.yqmonline.extensions.whenTypeIs
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class InventoryRowFragment(
    width: Int,
    item: GameItem,
) : Fragment {
    val dropButton =
        Components
            .button()
            .withText("Drop")
            .withDecorations()
            .build()

    val eatButton =
        Components
            .button()
            .withDecorations()
            .withText("Eat")
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
                item.whenTypeIs<Food> { addComponent(eatButton) }
            }
}
