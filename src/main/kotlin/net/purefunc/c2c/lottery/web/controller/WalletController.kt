package net.purefunc.c2c.lottery.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.dto.WalletDto
import net.purefunc.c2c.lottery.ext.return200
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Wallet 頁面")
@RestController
@RequestMapping("/api/v1.0/wallet")
@SecurityRequirement(name = "BearerAuth")
class WalletController(
    private val walletDao: WalletDao,
) {

    @Operation(summary = "取得錢包資訊")
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getWallet(
        principal: Principal,
    ) = WalletDto.queryByEmail(walletDao, principal.name).return200()
}