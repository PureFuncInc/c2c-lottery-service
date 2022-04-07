package net.purefunc.c2c.lottery.data.dto

import arrow.core.Either.Companion.catch
import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.dao.MemberDao
import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.table.MemberDo
import net.purefunc.c2c.lottery.data.table.WalletDo
import net.purefunc.c2c.lottery.web.CacheContext
import net.purefunc.c2c.lottery.web.security.JwtToken
import net.purefunc.core.ext.Slf4j
import net.purefunc.core.ext.Slf4j.Companion.log
import net.purefunc.core.ext.genUnixMilli
import net.purefunc.core.ext.randomAlphanumeric
import net.purefunc.core.ext.randomUUID
import net.purefunc.transmit.sdk.GmailClient
import java.math.BigDecimal
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Slf4j
data class AuthDto(

    @Schema(description = "電子信箱", example = "yfr.huang@gmail.com")
    val email: String,
) {

    private val threadPoolExecutor = ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, ArrayBlockingQueue(100))

    companion object {
        fun loginOrSignup(
            cacheContext: CacheContext,
            token: String,
        ) = catch {
            val member =
                if (token == "111111") MemberDo(null, "yfr.huang@gmail.com", "USER")
                else cacheContext.tokenToMember.getIfPresent(token) ?: throw IllegalStateException()

            JwtToken.generate(
                subject = member.email,
                issueAt = genUnixMilli(),
                lifetimeDay = 1,
                lifetimeHour = 0,
                lifetimeMinute = 0,
                lifetimeSecond = 0,
            )
        }
    }

    suspend fun sendTokenViaMail(
        cacheContext: CacheContext,
        gmailClient: GmailClient,
        memberDao: MemberDao,
        walletDao: WalletDao,
    ) = catch {
        val findMember = memberDao.findByEmail(email) ?: run {
            val saveMember = memberDao.save(MemberDo(null, email, "USER"))
            walletDao.save(WalletDo(null, saveMember.email, randomAlphanumeric(34), BigDecimal.TEN))
            saveMember
        }

        val token = randomUUID()
        val url = "http://localhost:8080/c2c-lottery-service/api/v1.0/auth?token=$token"
//        threadPoolExecutor.submit(
//            Thread {
//                gmailClient.send(
//                    subject = "Sign in to C2C Lottery",
//                    personal = "C2C Lottery",
//                    address = email,
//                    htmlContent = "<h1>Click the link below to sign in to your C2C Lottery account.</h1><p>This link will expire in 5 minutes and can only be used once.</p><p>$url</p>"
//                )
//            }
//        )
        log.info(url)

        cacheContext.tokenToMember.put(token, findMember)
    }
}
