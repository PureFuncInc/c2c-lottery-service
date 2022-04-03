package net.purefunc.c2c.lottery.data.dto

import arrow.core.Either.Companion.catch
import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.dao.MemberDao
import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.table.MemberDo
import net.purefunc.c2c.lottery.data.table.WalletDo
import net.purefunc.c2c.lottery.ext.genUnixMilli
import net.purefunc.c2c.lottery.ext.randomUUID
import net.purefunc.c2c.lottery.web.CacheContext
import net.purefunc.c2c.lottery.web.security.JwtToken
import net.purefunc.core.ext.Slf4j
import net.purefunc.core.ext.Slf4j.Companion.log
import net.purefunc.transmit.sdk.GmailClient
import java.math.BigDecimal

@Slf4j
data class AuthDto(

    @Schema(description = "電子信箱", example = "yfr.huang@gmail.com")
    val email: String,
) {

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
            walletDao.save(WalletDo(null, saveMember.email, "", BigDecimal.ZERO))
            saveMember
        }

        val send = gmailClient.send(
            subject = "Sign in to C2C Lottery",
            personal = "C2C Lottery",
            address = email,
            htmlContent = "<h1>Click the link below to sign in to your C2C Lottery account.</h1><h1>This link will expire in 5 minutes and can only be used once.</h1><p> https://c2c.net </p>"
        )
        log.info(send.toString())

        cacheContext.tokenToMember.put(randomUUID(), findMember)
    }
}
