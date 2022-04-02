package net.purefunc.c2c.lottery.data.dto

import net.purefunc.c2c.lottery.data.dto.response.GameDtoRes
import net.purefunc.c2c.lottery.data.enu.SportType
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.data.table.GameDo
import net.purefunc.c2c.lottery.data.vo.BetItem
import net.purefunc.c2c.lottery.ext.randomUUID

data class GameDto(

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val betItems: List<BetItem>,

    val startDate: Long,

    val endDate: Long,
) {

    companion object {
        suspend fun queryByUuid(gameRepository: GameRepository, uuid: String) = gameRepository.findByUuid(uuid)
    }

    suspend fun addGame(gameRepository: GameRepository) = gameRepository.save(this)

    fun toGameDo() =
        GameDo(
            id = null,
            uuid = randomUUID(),
            guestName = guestName,
            hostName = hostName,
            sportType = sportType,
            startDate = startDate,
            endDate = endDate,
        )

    fun toGameDtoRes(uuid: String) =
        GameDtoRes(
            uuid = uuid,
            guestName = guestName,
            hostName = hostName,
            sportType = sportType,
            betItems = betItems,
            startDate = startDate,
            endDate = endDate,
        )
}
