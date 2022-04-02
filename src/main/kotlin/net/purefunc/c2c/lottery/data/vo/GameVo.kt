package net.purefunc.c2c.lottery.data.vo

import net.purefunc.c2c.lottery.data.enu.BetType
import net.purefunc.c2c.lottery.data.enu.SportType
import java.math.BigDecimal

data class GameVo(

    val uuid: String,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val type: BetType,

    val value: String,

    val odds: BigDecimal,

    val endSubmitDate: Long,
)