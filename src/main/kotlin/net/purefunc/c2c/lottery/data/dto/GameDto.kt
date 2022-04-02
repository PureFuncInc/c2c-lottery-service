package net.purefunc.c2c.lottery.data.dto

import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.dto.response.GameDtoRes
import net.purefunc.c2c.lottery.data.enu.SportType
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.data.table.GameDo
import net.purefunc.c2c.lottery.data.vo.BetItemVo
import net.purefunc.c2c.lottery.ext.randomUUID

data class GameDto(

    @Schema(description = "客隊名稱", example = "夏洛特黃蜂")
    val guestName: String,

    @Schema(description = "主隊名稱", example = "費城76人")
    val hostName: String,

    @Schema(description = "運動類型", example = "BASKETBALL")
    val sportType: SportType,

    @Schema(description = "投注項目")
    val betItems: List<BetItemVo>,

    @Schema(description = "截止投注時間 (Unix Time Milli)", example = "1648909996710")
    val endSubmitDate: Long,
) {

    companion object {
        suspend fun queryByUuid(gameRepository: GameRepository, uuid: String) = gameRepository.findByUuid(uuid)

        suspend fun queryAll(gameRepository: GameRepository) = gameRepository.findAll()
    }

    suspend fun addGame(gameRepository: GameRepository) = gameRepository.save(this)

    fun toGameDo() =
        GameDo(
            id = null,
            uuid = randomUUID(),
            guestName = guestName,
            hostName = hostName,
            sportType = sportType,
            endSubmitDate = endSubmitDate,
        )

    fun toGameDtoRes(uuid: String) =
        GameDtoRes(
            uuid = uuid,
            guestName = guestName,
            hostName = hostName,
            sportType = sportType,
            betItems = betItems,
            endSubmitDate = endSubmitDate,
        )
}
