package com.yqmonline.systems

import com.yqmonline.attributes.types.CombatItem
import com.yqmonline.attributes.types.EnergyUser
import com.yqmonline.attributes.types.EquipmentHolder
import com.yqmonline.attributes.types.Food
import com.yqmonline.attributes.types.equip
import com.yqmonline.attributes.types.inventory
import com.yqmonline.config.GameConfig
import com.yqmonline.extensions.GameItem
import com.yqmonline.extensions.MessageFacet
import com.yqmonline.extensions.whenTypeIs
import com.yqmonline.messages.DropItem
import com.yqmonline.messages.Eat
import com.yqmonline.messages.InspectInventory
import com.yqmonline.view.fragment.InventoryFragment
import com.yqmonline.view.fragment.dialog.ExamineDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.platform.Dispatchers
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object InventoryInspector : MessageFacet<InspectInventory>(InspectInventory::class) {
    val DIALOG_SIZE = Size.create(60, 15)

    override suspend fun receive(message: InspectInventory): Response {
        val (context, itemHolder, position) = message

        val screen = context.screen

        val panel =
            Components
                .panel()
                .withSize(DIALOG_SIZE)
                .withDecorations(box(title = "Inventory"), shadow())
                .build()

        val fragment =
            InventoryFragment(
                inventory = itemHolder.inventory,
                width = DIALOG_SIZE.width - 3,
                onDrop = { item ->
                    CoroutineScope(Dispatchers.Single).launch {
                        itemHolder.receiveMessage(DropItem(context, itemHolder, item, position))
                    }
                },
                onEat = { item ->
                    CoroutineScope(Dispatchers.Single).launch {
                        itemHolder.whenTypeIs<EnergyUser> { eater ->
                            item.whenTypeIs<Food> { food ->
                                itemHolder.inventory.removeItem(food)
                                itemHolder.receiveMessage(Eat(context, eater, food))
                            }
                        }
                    }
                },
                onEquip = { item ->
                    var result = Maybe.empty<GameItem>()
                    itemHolder.whenTypeIs<EquipmentHolder> { equipmentHolder ->
                        item.whenTypeIs<CombatItem> { combatItem ->
                            result = Maybe.of(equipmentHolder.equip(itemHolder.inventory, combatItem))
                        }
                    }
                    result
                },
                onExamine = { item -> screen.openModal(ExamineDialog(screen, item)) },
            )

        panel.addFragment(fragment)

        val modal =
            ModalBuilder
                .newBuilder<EmptyModalResult>()
                .withParentSize(screen.size)
                .withComponent(panel)
                .withCenteredDialog(true)
                .build()

        panel.addComponent(
            Components
                .button()
                .withText("Close")
                .withAlignmentWithin(panel, ComponentAlignment.BOTTOM_LEFT)
                .build()
                .apply {
                    onActivated {
                        modal.close(EmptyModalResult)
                        Processed
                    }
                },
        )

        modal.theme = GameConfig.THEME
        screen.openModal(modal)
        return Consumed
    }
}
