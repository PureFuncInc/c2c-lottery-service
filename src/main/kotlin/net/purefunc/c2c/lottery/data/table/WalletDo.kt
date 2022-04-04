package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.dto.PersonDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("wallet")
data class WalletDo(

    @Id
    val id: Long?,

    var email: String,

    var address: String,

    var balance: BigDecimal,
) {

    fun toDto() =
        PersonDto(
            email = email,
            address = address,
            balance = balance,
        )
}