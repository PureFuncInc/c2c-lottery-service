package net.purefunc.c2c.lottery.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import net.purefunc.c2c.lottery.data.dao.WalletDao
import net.purefunc.c2c.lottery.data.dto.PersonDto
import net.purefunc.c2c.lottery.ext.return200
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Person 頁面")
@RestController
@RequestMapping("/api/v1.0/person")
@SecurityRequirement(name = "BearerAuth")
class PersonController(
    private val walletDao: WalletDao,
) {

    @Operation(summary = "取得個人資訊")
    @GetMapping("/info")
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getInfo(
        principal: Principal,
    ) = PersonDto.queryByEmail(walletDao, principal.name).return200()
}