package net.purefunc.c2c.lottery.data.repository

import arrow.core.Either
import net.purefunc.c2c.lottery.data.dto.MatchDto
import net.purefunc.c2c.lottery.data.dto.response.MatchDtoRes

interface MatchRepository {

    suspend fun findByUuid(uuid: String): Either<Throwable, MatchDtoRes>

    suspend fun findAll(): Either<Throwable, List<MatchDtoRes>>

    suspend fun save(matchDto: MatchDto): Either<Throwable, MatchDtoRes>
}