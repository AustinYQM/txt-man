package com.yqmonline.view.fragment

import com.yqmonline.attributes.Inventory
import com.yqmonline.config.GameConfig
import com.yqmonline.extensions.GameItem
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.VBox

class InventoryFragment(
    inventory: Inventory,
    width: Int,
    private val onDrop: (GameItem) -> Unit,
    private val onEat: (GameItem) -> Unit,
    private val onEquip: (GameItem) -> Maybe<GameItem>,
) : Fragment {
    override val root =
        Components
            .vbox()
            .withSize(width, inventory.size + 1)
            .build()
            .apply {
                val list = this
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
                    addRow(width, item, list)
                }
            }

    private fun addRow(
        width: Int,
        item: GameItem,
        list: VBox,
    ) {
        val row = InventoryRowFragment(width, item)
        list.addFragment(row).apply {
            row.dropButton.onActivated {
                detach()
                onDrop(item)
            }
            row.eatButton.onActivated {
                detach()
                onEat(item)
            }
            row.equipButton.onActivated {
                onEquip(item).map { oldItem ->
                    detach()
                    addRow(width, oldItem, list)
                }
            }
        }
        list.theme = GameConfig.THEME
    }

    companion object {
        const val NAME_COLUMN_WIDTH = 15
        const val ACTIONS_COLUMN_WIDTH = 10
    }
}
