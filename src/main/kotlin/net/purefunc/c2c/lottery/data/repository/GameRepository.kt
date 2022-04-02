package net.purefunc.c2c.lottery.data.repository

import arrow.core.Either
import net.purefunc.c2c.lottery.data.dto.GameDto
import net.purefunc.c2c.lottery.data.dto.response.GameDtoRes

interface GameRepository {

    suspend fun findByUuid(uuid: String): Either<Throwable, GameDtoRes>

    suspend fun findAll(page: Int, size: Int): Either<Throwable, List<GameDtoRes>>

    suspend fun save(gameDto: GameDto): Either<Throwable, GameDtoRes>
}