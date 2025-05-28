package com.yqmonline.attributes

import com.yqmonline.config.GameConfig.MAXIMUM_FUNGUS_SPREAD
import org.hexworks.amethyst.api.base.BaseAttribute

/**
 * @property spreadCount
 * @property maximumSpread
 */
data class FungusSpread(
    var spreadCount: Int = 0,
    val maximumSpread: Int = MAXIMUM_FUNGUS_SPREAD,
) : BaseAttribute()
