package com.yqmonline.view.fragment.dialog

import com.yqmonline.attributes.CombatStats
import com.yqmonline.attributes.Vision
import com.yqmonline.entities.Player
import com.yqmonline.events.logGameEvent
import com.yqmonline.extensions.GameEntity
import com.yqmonline.extensions.tryToFindAttribute
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

class LevelUpDialog(
    private val screen: Screen,
    player: GameEntity<Player>,
) : Dialog(screen, false) {
    override val container =
        Components
            .vbox()
            .withDecorations(box(title = "Ding!", boxType = BoxType.TOP_BOTTOM_DOUBLE))
            .withSize(30, 15)
            .build()
            .apply {
                val stats = player.tryToFindAttribute(CombatStats::class)
                val vision = player.tryToFindAttribute(Vision::class)

                addComponent(
                    Components
                        .textBox(27)
                        .addHeader("Congratulations, you leveled up!")
                        .addParagraph("Pick an improvement from the options below:"),
                )

                addComponent(
                    Components
                        .button()
                        .withText("Max HP")
                        .build()
                        .apply {
                            onActivated {
                                stats.maxHpProperty.value += 10
                                logGameEvent("You look healthier.", this)
                                root.close(EmptyModalResult)
                            }
                        },
                )

                addComponent(
                    Components
                        .button()
                        .withText("Attack")
                        .build()
                        .apply {
                            onActivated {
                                stats.attackValueProperty.value += 2
                                logGameEvent("You look stronger.", this)
                                root.close(EmptyModalResult)
                            }
                        },
                )

                addComponent(
                    Components
                        .button()
                        .withText("Defense")
                        .build()
                        .apply {
                            onActivated {
                                stats.defenseValueProperty.value += 2
                                logGameEvent("You look tougher.", this)
                                root.close(EmptyModalResult)
                            }
                        },
                )

                addComponent(
                    Components
                        .button()
                        .withText("Vision")
                        .build()
                        .apply {
                            onActivated {
                                vision.radius++
                                logGameEvent("You look more perceptive.", this)
                                root.close(EmptyModalResult)
                            }
                        },
                )
            }
}
