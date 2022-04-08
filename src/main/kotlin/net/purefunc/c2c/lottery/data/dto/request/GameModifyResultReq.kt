package net.purefunc.c2c.lottery.data.dto.request

import net.purefunc.c2c.lottery.data.repository.GameRepository

data class GameModifyResultReq(

    val gameUuid: String,

    val betItemUuids: List<String>,
) {

    suspend fun modify(
        gameRepository: GameRepository,
        email: String,
    ) = gameRepository.updateBetItemsResult(gameUuid, betItemUuids, email)
}