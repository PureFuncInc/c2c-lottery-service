package net.purefunc.c2c.lottery.data.repository

import java.math.BigDecimal

interface WalletRepository {

    suspend fun payForOrder(email: String, orderUuid: String, amount: BigDecimal): String

    suspend fun deposit(email: String, amount: BigDecimal): String

    suspend fun withdraw(email: String, amount: BigDecimal): String
}