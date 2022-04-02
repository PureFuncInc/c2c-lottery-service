package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.enu.SportType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("game")
data class MatchDo(

    @Id
    val id: Long?,

    val uuid: String,

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val startDate: Long,

    val endDate: Long,
)