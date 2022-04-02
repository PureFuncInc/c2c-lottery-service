package net.purefunc.c2c.lottery.web.controller

import net.purefunc.c2c.lottery.data.dto.MatchDto
import net.purefunc.c2c.lottery.data.repository.MatchRepository
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
@RequestMapping("/api/v1.0/matches")
class MatchController(
    private val matchRepository: MatchRepository,
) {

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getMatches(
        @PathVariable uuid: String,
        principal: Principal,
    ) = MatchDto.queryByUuid(matchRepository, uuid)

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun postMatches(
        @RequestBody matchDto: MatchDto,
        principal: Principal,
    ) = matchDto.addMatch(matchRepository).return200()
}