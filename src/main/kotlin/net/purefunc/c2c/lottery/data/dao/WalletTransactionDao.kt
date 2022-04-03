package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.table.WalletTransactionDo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface WalletTransactionDao : CoroutineCrudRepository<WalletTransactionDo, Long> {
}
