package com.yqmonline.attributes

import com.yqmonline.extensions.toStringProperty
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components

data class CombatStats(
    val maxHpProperty: Property<Int>,
    val hpProperty: Property<Int> = createPropertyFrom(maxHpProperty.value),
    val attackValueProperty: Property<Int>,
    val defenseValueProperty: Property<Int>,
) : BaseAttribute(),
    DisplayableAttribute {
    val maxHp: Int by maxHpProperty.asDelegate()
    var hp: Int by hpProperty.asDelegate()
    val attackValue by attackValueProperty.asDelegate()
    val defenseValue by defenseValueProperty.asDelegate()

    override fun toComponent(width: Int) =
        Components
            .vbox()
            .withSize(width, 5)
            .build()
            .apply {
                val hpLabel =
                    Components
                        .label()
                        .withSize(width, 1)
                        .build()

                val attackLabel =
                    Components
                        .label()
                        .withSize(width, 1)
                        .build()

                val defenseLabel =
                    Components
                        .label()
                        .withSize(width, 1)
                        .build()

                hpLabel.textProperty.updateFrom(
                    createPropertyFrom("HP: ")
                        .bindPlusWith(hpProperty.toStringProperty())
                        .bindPlusWith("/".toProperty())
                        .bindPlusWith(maxHpProperty.toStringProperty()),
                )

                attackLabel.textProperty.updateFrom(
                    createPropertyFrom("Att: ").bindPlusWith(attackValueProperty.toStringProperty()),
                )

                defenseLabel.textProperty.updateFrom(
                    createPropertyFrom("Def: ")
                        .bindPlusWith(defenseValueProperty.toStringProperty()),
                )

                addComponent(Components.textBox(width).addHeader("Combat Stats"))
                addComponent(hpLabel)
                addComponent(attackLabel)
                addComponent(defenseLabel)
            }

    companion object {
        fun create(
            maxHp: Int,
            hp: Int = maxHp,
            attackValue: Int,
            defenseValue: Int,
        ) = CombatStats(
            maxHpProperty = createPropertyFrom(maxHp),
            hpProperty = createPropertyFrom(hp),
            attackValueProperty = createPropertyFrom(attackValue),
            defenseValueProperty = createPropertyFrom(defenseValue),
        )
    }
}
