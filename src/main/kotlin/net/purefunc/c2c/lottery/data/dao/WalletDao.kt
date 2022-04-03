package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.table.WalletDo
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal

interface WalletDao : CoroutineCrudRepository<WalletDo, Long> {

    suspend fun findByEmail(email: String): WalletDo?

    @Modifying
    @Query("UPDATE wallet SET balance = balance + :amount WHERE email = :email")
    suspend fun updateBalanceByEmail(email: String, amount: BigDecimal): WalletDo?
}
