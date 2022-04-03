package net.purefunc.c2c.lottery.data.table

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("slip")
data class SlipDo(

    @Id
    val id: Long?,

    var uuid: String,

    var orderId: Long,

    var betItemId: Long,
)