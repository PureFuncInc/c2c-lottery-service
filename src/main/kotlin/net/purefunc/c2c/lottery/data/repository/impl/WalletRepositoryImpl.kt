package net.purefunc.c2c.lottery.data.repository.impl

import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.dao.WalletTransactionDao
import net.purefunc.c2c.lottery.data.enu.TransactionType
import net.purefunc.c2c.lottery.data.repository.WalletRepository
import net.purefunc.c2c.lottery.data.table.WalletTransactionDo
import net.purefunc.c2c.lottery.ext.genUnixMilli
import net.purefunc.c2c.lottery.ext.randomUUID
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class WalletRepositoryImpl(
    private val walletDao: WalletDao,
    private val walletTransactionDao: WalletTransactionDao,
) : WalletRepository {

    override suspend fun payForOrder(email: String, orderUuid: String, amount: BigDecimal) =
        run {
            val wallet = walletDao.findByEmail(email) ?: throw IllegalStateException()
            walletDao.updateBalanceByEmail(email, amount)
            val tx = walletTransactionDao.save(
                WalletTransactionDo(
                    id = null,
                    uuid = randomUUID(),
                    orderUuid = orderUuid,
                    walletId = wallet.id!!,
                    type = TransactionType.ORDER,
                    beforeBalance = wallet.balance,
                    amount = amount,
                    afterBalance = wallet.balance.minus(amount),
                    createDate = genUnixMilli()
                )
            )

            tx.uuid
        }

    override suspend fun deposit(email: String, amount: BigDecimal) =
        run {
            val wallet = walletDao.findByEmail(email) ?: throw IllegalStateException()
            walletDao.updateBalanceByEmail(email, amount)
            val tx = walletTransactionDao.save(
                WalletTransactionDo(
                    id = null,
                    uuid = randomUUID(),
                    orderUuid = "",
                    walletId = wallet.id!!,
                    type = TransactionType.DEPOSIT,
                    beforeBalance = wallet.balance,
                    amount = amount,
                    afterBalance = wallet.balance.plus(amount),
                    createDate = genUnixMilli()
                )
            )

            tx.uuid
        }

    override suspend fun withdraw(email: String, amount: BigDecimal) =
        run {
            val wallet = walletDao.findByEmail(email) ?: throw IllegalStateException()
            walletDao.updateBalanceByEmail(email, amount.negate())
            val tx = walletTransactionDao.save(
                WalletTransactionDo(
                    id = null,
                    uuid = randomUUID(),
                    orderUuid = "",
                    walletId = wallet.id!!,
                    type = TransactionType.WITHDRAW,
                    beforeBalance = wallet.balance,
                    amount = amount,
                    afterBalance = wallet.balance.minus(amount),
                    createDate = genUnixMilli()
                )
            )

            tx.uuid
        }
}