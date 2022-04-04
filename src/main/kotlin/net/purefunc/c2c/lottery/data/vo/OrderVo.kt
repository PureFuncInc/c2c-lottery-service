package net.purefunc.c2c.lottery.data.vo

import net.purefunc.c2c.lottery.data.enu.BetType
import net.purefunc.c2c.lottery.data.enu.OrderStatus
import net.purefunc.c2c.lottery.data.enu.SportType
import java.math.BigDecimal

data class OrderVo(

    val uuid: String,

    val email: String,

    val combination: String,

    val multiple: Int,

    val totalAmount: Int,

    val winAmount: BigDecimal,

    val status: OrderStatus,

    val createDate: Long,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    var type: BetType,

    var value: String,

    var odds: BigDecimal,
)