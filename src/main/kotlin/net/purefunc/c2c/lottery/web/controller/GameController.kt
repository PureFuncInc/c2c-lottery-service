package net.purefunc.c2c.lottery.web.controller

import net.purefunc.c2c.lottery.data.dto.GameDto
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.ext.return200
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1.0/games")
class GameController(
    private val gameRepository: GameRepository,
) {

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getGames(
        @PathVariable uuid: String,
        principal: Principal,
    ) = GameDto.queryByUuid(gameRepository, uuid)

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun postGames(
        @RequestBody gameDto: GameDto,
        principal: Principal,
    ) = gameDto.addGame(gameRepository).return200()
}