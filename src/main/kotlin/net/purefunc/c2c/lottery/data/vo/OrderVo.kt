package net.purefunc.c2c.lottery.data.vo

import net.purefunc.c2c.lottery.data.enu.BetType
import net.purefunc.c2c.lottery.data.enu.OrderType
import net.purefunc.c2c.lottery.data.enu.SportType
import java.math.BigDecimal

data class OrderVo(

    val uuid: String,

    val email: String,

    val orderType: OrderType,

    val multiple: Int,

    val createDate: Long,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    var type: BetType,

    var value: String,

    var odds: BigDecimal,
)