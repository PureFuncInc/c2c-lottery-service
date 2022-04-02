package net.purefunc.c2c.lottery.data.dto

import net.purefunc.c2c.lottery.data.dto.response.MatchDtoRes
import net.purefunc.c2c.lottery.data.enu.SportType
import net.purefunc.c2c.lottery.data.repository.MatchRepository
import net.purefunc.c2c.lottery.data.table.MatchDo
import net.purefunc.c2c.lottery.data.vo.BetItem
import net.purefunc.c2c.lottery.ext.randomUUID

data class MatchDto(

    val guestName: String,

    val hostName: String,

    val sportType: SportType,

    val betItems: List<BetItem>,

    val startDate: Long,

    val endDate: Long,
) {

    companion object {
        suspend fun queryByUuid(matchRepository: MatchRepository, uuid: String) = matchRepository.findByUuid(uuid)
    }

    suspend fun addMatch(matchRepository: MatchRepository) = matchRepository.save(this)

    fun toMatchDo() =
        MatchDo(
            id = null,
            uuid = randomUUID(),
            guestName = guestName,
            hostName = hostName,
            sportType = sportType,
            startDate = startDate,
            endDate = endDate,
        )

    fun toMatchDtoRes(uuid: String) =
        MatchDtoRes(
            uuid = uuid,
            guestName = guestName,
            hostName = hostName,
            sportType = sportType,
            betItems = betItems,
            startDate = startDate,
            endDate = endDate,
        )
}
