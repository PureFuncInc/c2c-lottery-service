package net.purefunc.c2c.lottery.web.controller

import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.dto.WalletDto
import net.purefunc.c2c.lottery.ext.return200
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1.0/wallet")
class WalletController(
    private val walletDao: WalletDao,
) {

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getWallet(
        principal: Principal,
    ) = WalletDto.queryByEmail(walletDao, principal.name!!).return200()
}