package net.purefunc.c2c.lottery.data.vo

import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import net.purefunc.c2c.lottery.data.enu.BetType
import net.purefunc.c2c.lottery.data.enu.SportType
import java.math.BigDecimal

data class GameVo(

    val uuid: String,

    val owner: String,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val endSubmitDate: Long,

    val betItemUuid: String,

    val type: BetType,

    val value: String,

    val odds: BigDecimal,

    val status: BetItemStatus,
)