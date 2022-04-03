package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.enu.OrderType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("sport_order")
data class OrderDo(

    @Id
    val id: Long?,

    var uuid: String,

    var email: String,

    var type: OrderType,

    var multiple: Int,

    var createDate: Long,
)