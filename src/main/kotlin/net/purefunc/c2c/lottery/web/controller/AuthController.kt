package net.purefunc.c2c.lottery.web.controller

import net.purefunc.c2c.lottery.data.dao.MemberDao
import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.dto.AuthDto
import net.purefunc.c2c.lottery.ext.return202
import net.purefunc.c2c.lottery.ext.returnToken
import net.purefunc.c2c.lottery.web.CacheContext
import net.purefunc.transmit.sdk.GmailClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1.0/auth")
class AuthController(
    private val cacheContext: CacheContext,
    private val gmailClient: GmailClient,
    private val memberDao: MemberDao,
    private val walletDao: WalletDao,
) {

    @GetMapping
    fun getAuth(
        @RequestParam token: String,
    ) = AuthDto.loginOrSignup(cacheContext, token).returnToken()

    @PostMapping
    suspend fun postAuth(
        @RequestBody authDto: AuthDto,
    ) = authDto.sendTokenViaMail(cacheContext, gmailClient, memberDao, walletDao).return202()
}