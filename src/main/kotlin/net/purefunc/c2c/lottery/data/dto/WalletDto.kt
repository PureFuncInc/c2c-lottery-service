package net.purefunc.c2c.lottery.data.dto

import arrow.core.Either.Companion.catch
import net.purefunc.c2c.lottery.data.dao.WalletDao
import java.math.BigDecimal


data class WalletDto(

    val email: String,

    val address: String,

    val balance: BigDecimal,
) {

    companion object {
        suspend fun queryByEmail(walletDao: WalletDao, email: String) =
            catch {
                walletDao.findByEmail(email) ?: throw IllegalStateException("")
            }
    }
}
