package net.purefunc.c2c.lottery.data.dto.response

import net.purefunc.c2c.lottery.data.enu.SportType

data class GameDtoRes(

    val uuid: String,

    val owner: String,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val betItems: List<BetItemDtoRes>,

    val endSubmitDate: Long,
)