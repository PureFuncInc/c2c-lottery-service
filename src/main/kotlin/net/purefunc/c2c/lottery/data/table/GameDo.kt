package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.enu.SportType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("game")
data class GameDo(

    @Id
    val id: Long?,

    var uuid: String,

    var guestName: String,

    var hostName: String,

    var sportType: SportType,

    var endSubmitDate: Long,
)