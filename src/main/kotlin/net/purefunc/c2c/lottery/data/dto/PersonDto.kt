package net.purefunc.c2c.lottery.data.dto

import arrow.core.Either.Companion.catch
import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.dao.WalletDao
import java.math.BigDecimal

data class PersonDto(

    @Schema(description = "帳號(電子郵件)", example = "yfr.huang@gmail.com")
    val email: String,

    @Schema(description = "錢包地址", example = "58w3KjbifNR84e0cC8YGItxyAMl2VeKaHl")
    val address: String,

    @Schema(description = "餘額", example = "10.0000")
    val balance: BigDecimal,
) {

    companion object {
        suspend fun queryByEmail(walletDao: WalletDao, email: String) =
            catch {
                walletDao.findByEmail(email)?.toDto() ?: throw IllegalStateException()
            }
    }
}
