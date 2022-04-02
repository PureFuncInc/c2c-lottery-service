package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.enu.BetType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("bet_item")
data class BetItemDo(

    @Id
    val id: Long?,

    var matchId: Long,

    var type: BetType,

    var value: String,

    var odds: BigDecimal,
)