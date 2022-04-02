package net.purefunc.c2c.lottery.data.dto.response

import net.purefunc.c2c.lottery.data.enu.SportType
import net.purefunc.c2c.lottery.data.vo.BetItem

data class MatchDtoRes(

    val uuid: String,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val betItems: List<BetItem>,

    val startDate: Long,

    val endDate: Long,
)