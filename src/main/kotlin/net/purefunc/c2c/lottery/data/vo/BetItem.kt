package net.purefunc.c2c.lottery.data.vo

import net.purefunc.c2c.lottery.data.enu.BetType
import java.math.BigDecimal

data class BetItem(

    val type: BetType,

    val value: String,

    val odds: BigDecimal,
)
