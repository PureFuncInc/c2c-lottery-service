package net.purefunc.c2c.lottery.data.repository.impl

import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.dao.WalletTransactionDao
import net.purefunc.c2c.lottery.data.enu.TransactionType
import net.purefunc.c2c.lottery.data.repository.WalletRepository
import net.purefunc.c2c.lottery.data.table.WalletTransactionDo
import net.purefunc.core.ext.genUnixMilli
import net.purefunc.core.ext.randomUUID
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class WalletRepositoryImpl(
    private val walletDao: WalletDao,
    private val walletTransactionDao: WalletTransactionDao,
) : WalletRepository {

    override suspend fun payForOrder(
        email: String,
        orderUuid: String,
        amount: Int,
    ) = operate(TransactionType.ORDER, email, orderUuid, amount.toBigDecimal())

    override suspend fun cancelOrder(
        email: String,
        orderUuid: String,
        amount: Int,
    ) = operate(TransactionType.CANCEL_ORDER, email, orderUuid, amount.toBigDecimal())

    override suspend fun deposit(
        email: String,
        amount: BigDecimal,
    ) = operate(TransactionType.DEPOSIT, email, "", amount)

    override suspend fun withdraw(
        email: String,
        amount: BigDecimal,
    ) = operate(TransactionType.WITHDRAW, email, "", amount)

    suspend fun operate(
        transactionType: TransactionType,
        email: String,
        orderUuid: String,
        amount: BigDecimal,
    ) = run {
        val opAmount = when (transactionType) {
            TransactionType.ORDER -> amount.negate()
            TransactionType.CANCEL_ORDER -> amount
            TransactionType.DEPOSIT -> amount
            TransactionType.WITHDRAW -> amount.negate()
        }

        val wallet = walletDao.findByEmail(email) ?: throw IllegalStateException()
        walletDao.updateBalanceByEmail(email, opAmount)
        val tx = walletTransactionDao.save(
            WalletTransactionDo(
                id = null,
                uuid = randomUUID(),
                orderUuid = orderUuid,
                walletId = wallet.id!!,
                type = transactionType,
                beforeBalance = wallet.balance,
                amount = opAmount,
                afterBalance = wallet.balance.plus(opAmount),
                createDate = genUnixMilli()
            )
        )

        tx.uuid
    }
}