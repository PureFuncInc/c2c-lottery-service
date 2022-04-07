package net.purefunc.c2c.lottery.data.dto

import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.enu.SportType
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.data.table.GameDo
import net.purefunc.core.ext.randomUUID

data class GameDto(

    @Schema(description = "客隊名稱", example = "夏洛特黃蜂")
    val guestName: String,

    @Schema(description = "主隊名稱", example = "費城76人")
    val hostName: String,

    @Schema(description = "運動類型", example = "BASKETBALL")
    val sportType: SportType,

    @Schema(description = "投注項目")
    val betItems: List<BetItemDto>,

    @Schema(description = "截止投注時間 (Unix Time Milli)", example = "1648909996710")
    val endSubmitDate: Long,
) {

    companion object {
        suspend fun queryByUuid(gameRepository: GameRepository, uuid: String) = gameRepository.findByUuid(uuid)

        suspend fun queryAll(gameRepository: GameRepository, page: Int, size: Int) = gameRepository.findAll(page, size)
    }

    suspend fun add(gameRepository: GameRepository, email: String) = gameRepository.save(this, email)

    fun toGameDo(email: String) =
        GameDo(
            id = null,
            uuid = randomUUID(),
            owner = email,
            guestName = guestName,
            hostName = hostName,
            sportType = sportType,
            endSubmitDate = endSubmitDate,
        )
}
