package net.purefunc.c2c.lottery.data.dto.response

import net.purefunc.c2c.lottery.data.enu.BetType
import java.math.BigDecimal

data class BetItemDtoRes(

    val uuid: String,

    val type: BetType,

    val value: String,

    val odds: BigDecimal,
)