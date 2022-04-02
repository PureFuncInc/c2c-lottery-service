package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.table.WalletDo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface WalletDao : CoroutineCrudRepository<WalletDo, Long> {

    suspend fun findByEmail(email: String): WalletDo?
}
