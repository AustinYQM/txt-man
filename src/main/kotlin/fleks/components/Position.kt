package com.yqmonline.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.data.Position3D

class Position(
    initialPosition: Position3D = Position3D.unknown(),
) : Component<Position> {
    /**
     * returns the type of the [Position] component.
     * used by various parts of the system to find entities with the given
     * type in order to build things like families.
     */
    override fun type() = Position

    /**
     * The companion object of the [Position] component.
     * This stores the unique id of the [Position] component
     * allowing it to be grabbed directly O(1) with the id and to
     * help with recycling of enities and components.
     */
    companion object : ComponentType<Position>()

    private val positionProperty = initialPosition.toProperty()
    var position by positionProperty.asDelegate()
}
