package com.yqmonline.attributes

import com.yqmonline.extensions.toStringProperty
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.Components
import kotlin.math.min

class EnergyLevel(
    initialEnergy: Int,
    val maxEnergy: Int,
) : BaseAttribute(),
    DisplayableAttribute {
    var currentEnergy: Int
        get() = currentValueProperty.value
        set(value) {
            currentValueProperty.value = min(value, maxEnergy)
        }

    private val currentValueProperty = createPropertyFrom(initialEnergy)

    override fun toComponent(width: Int) =
        Components
            .vbox()
            .withSize(width, 5)
            .build()
            .apply {
                val hungerLabel = Components.label().withSize(width, 1).build()

                hungerLabel.textProperty.updateFrom(
                    currentValueProperty
                        .toStringProperty()
                        .bindPlusWith("/".toProperty())
                        .bindPlusWith(maxEnergy.toString().toProperty()),
                )

                addComponent(Components.textBox(width).addHeader("Hunger"))
                addComponent(hungerLabel)
            }
}
