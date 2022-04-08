package net.purefunc.c2c.lottery.data.repository

import arrow.core.Either
import net.purefunc.c2c.lottery.data.dto.GameDto
import net.purefunc.c2c.lottery.data.dto.response.GameDtoRes
import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import java.math.BigDecimal

interface GameRepository {

    suspend fun findByUuid(uuid: String): Either<Throwable, GameDtoRes>

    suspend fun findAll(page: Int, size: Int): Either<Throwable, List<GameDtoRes>>

    suspend fun save(gameDto: GameDto, email: String): Either<Throwable, String>

    suspend fun updateGameStatus(uuid: String, email: String, status: BetItemStatus): Either<Throwable, String>

    suspend fun updateBetItemsStatus(
        uuids: List<String>,
        email: String,
        status: BetItemStatus,
    ): Either<Throwable, List<String>>

    suspend fun updateBetItemsOdds(
        uuids: List<String>,
        email: String,
        odds: List<BigDecimal>,
    ): Either<Throwable, List<String>>

    suspend fun updateBetItemsResult(
        gameUuid: String,
        betItemUuids: List<String>,
        email: String,
    ): Either<Throwable, List<String>>
}