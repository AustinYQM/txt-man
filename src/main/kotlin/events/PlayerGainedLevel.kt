package com.yqmonline.events

import org.hexworks.cobalt.events.api.Event

data class PlayerGainedLevel(
    override val emitter: Any,
) : Event
