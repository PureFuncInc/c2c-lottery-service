package net.purefunc.c2c.lottery.data.dto.response

import net.purefunc.c2c.lottery.data.enu.OrderStatus
import java.math.BigDecimal

data class OrderDtoRes(

    val uuid: String,

    val email: String,

    val combination: List<String>,

    val multiple: Int,

    var totalAmount: Int,

    var winAmount: BigDecimal,

    var status: OrderStatus,

    val createDate: Long,

    val slips: List<SlipDtoRes>,
)
