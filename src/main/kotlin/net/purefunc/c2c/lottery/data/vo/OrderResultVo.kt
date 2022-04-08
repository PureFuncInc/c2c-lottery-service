package net.purefunc.c2c.lottery.data.vo

import net.purefunc.c2c.lottery.data.enu.BetItemStatus

data class OrderResultVo(

    val uuid: String,

    val status: BetItemStatus,
)