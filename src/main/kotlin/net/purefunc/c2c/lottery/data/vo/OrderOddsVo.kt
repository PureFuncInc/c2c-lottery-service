package net.purefunc.c2c.lottery.data.vo

import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import java.math.BigDecimal

data class OrderOddsVo(

    val uuid: String,

    val combination: String,

    val multiple: Int,

    val odds: BigDecimal,

    val betItemStatus: BetItemStatus,
)