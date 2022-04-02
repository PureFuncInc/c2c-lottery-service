package net.purefunc.c2c.lottery.data.table

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("member")
data class MemberDo(

    @Id
    val id: Long?,

    var email: String,

    var role: String,
)