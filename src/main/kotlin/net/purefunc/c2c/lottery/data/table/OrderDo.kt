package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.enu.OrderStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("sport_order")
data class OrderDo(

    @Id
    val id: Long?,

    var uuid: String,

    var email: String,

    var combination: String,

    var multiple: Int,

    var totalAmount: BigDecimal,

    var winAmount: BigDecimal,

    var status: OrderStatus,

    var createDate: Long,
)