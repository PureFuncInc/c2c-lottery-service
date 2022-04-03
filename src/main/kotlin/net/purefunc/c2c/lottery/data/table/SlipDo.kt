package net.purefunc.c2c.lottery.data.table

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("slip")
data class SlipDo(

    @Id
    val id: Long?,

    val uuid: String,

    val orderId: Long,

    val betItemId: Long,
)