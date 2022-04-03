package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.enu.OrderType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("sport_order")
data class OrderDo(

    @Id
    val id: Long?,

    val uuid: String,

    val email: String,

    val type: OrderType,

    val multiple: Int,

    val createDate: Long,
)