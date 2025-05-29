package com.yqmonline.view.fragment

import com.yqmonline.attributes.Inventory
import com.yqmonline.extensions.GameItem
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class InventoryFragment(
    inventory: Inventory,
    width: Int,
    onDrop: (GameItem) -> Unit,
) : Fragment {
    override val root =
        Components
            .vbox()
            .withSize(width, inventory.size + 1)
            .build()
            .apply {
                addComponent(
                    Components
                        .hbox()
                        .withSpacing(1)
                        .withSize(width, 1)
                        .build()
                        .apply {
                            addComponent(
                                Components
                                    .label()
                                    .withText("")
                                    .withSize(1, 1)
                                    .build(),
                            )
                            addComponent(
                                Components
                                    .header()
                                    .withText("Name")
                                    .withSize(NAME_COLUMN_WIDTH, 1)
                                    .build(),
                            )
                            addComponent(
                                Components
                                    .header()
                                    .withText("Actions")
                                    .withSize(ACTIONS_COLUMN_WIDTH, 1)
                                    .build(),
                            )
                        },
                )
                inventory.items.forEach { item ->
                    val row = InventoryRowFragment(width, item)
                    addFragment(row).apply {
                        row.dropButton.onActivated {
                            detach()
                            onDrop(item)
                        }
                    }
                }
            }

    companion object {
        const val NAME_COLUMN_WIDTH = 15
        const val ACTIONS_COLUMN_WIDTH = 10
    }
}
