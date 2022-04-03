package net.purefunc.c2c.lottery.data.table

import net.purefunc.c2c.lottery.data.enu.TransactionType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("wallet_transaction")
data class WalletTransactionDo(

    @Id
    val id: Long?,

    var uuid: String,

    var orderUuid: String,

    var walletId: Long,

    var type: TransactionType,

    var beforeBalance: BigDecimal,

    var amount: BigDecimal,

    var afterBalance: BigDecimal,

    var createDate: Long,
)