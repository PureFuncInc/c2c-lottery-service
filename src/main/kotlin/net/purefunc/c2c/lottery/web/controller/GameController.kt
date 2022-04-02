package net.purefunc.c2c.lottery.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import net.purefunc.c2c.lottery.data.dto.GameDto
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.ext.return200
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1.0/games")
@SecurityRequirement(name = "BearerAuth")
class GameController(
    private val gameRepository: GameRepository,
) {

    @Operation(summary = "根據 uuid 查詢賽事")
    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getGamesByUuid(
        @PathVariable uuid: String,
        principal: Principal,
    ) = GameDto.queryByUuid(gameRepository, uuid).return200()

    @Operation(summary = "查詢所有賽事")
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getGames(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        principal: Principal,
    ) = GameDto.queryAll(gameRepository, page, size).return200()

    @Operation(summary = "新增賽事")
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun postGames(
        @RequestBody gameDto: GameDto,
        principal: Principal,
    ) = gameDto.addGame(gameRepository).return200()
}