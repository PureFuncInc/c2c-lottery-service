package net.purefunc.c2c.lottery.data.dto.response

import net.purefunc.c2c.lottery.data.enu.SportType

data class GameDtoRes(

    val uuid: String,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val betItemUuids: List<String>,

    val endSubmitDate: Long,
)