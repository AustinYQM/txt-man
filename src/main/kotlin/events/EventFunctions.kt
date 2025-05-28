package com.yqmonline.events

import org.hexworks.zircon.internal.Zircon

fun logGameEvent(
    text: String,
    emitter: Any,
) {
    Zircon.eventBus.publish(GameLogEvent(text, emitter))
}
