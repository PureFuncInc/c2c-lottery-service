package net.purefunc.c2c.lottery.data.dto.response

import net.purefunc.c2c.lottery.data.enu.OrderType

data class OrderDtoRes(

    val uuid: String,

    val email: String,

    val orderType: OrderType,

    val multiple: Int,

    val createDate: Long,

    val slips: List<SlipDtoRes>,
)
