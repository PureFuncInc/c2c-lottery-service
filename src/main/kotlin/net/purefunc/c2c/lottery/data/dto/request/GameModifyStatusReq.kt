package net.purefunc.c2c.lottery.data.dto.request

import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import net.purefunc.c2c.lottery.data.repository.GameRepository

data class GameModifyStatusReq(

    val gameUuid: String,

    val status: BetItemStatus,
) {

    companion object {
        suspend fun modify(gameRepository: GameRepository, betItemUuids: List<String>, email: String) =
            gameRepository.updateBetItemsResult(betItemUuids, email)
    }

    suspend fun modifyGame(gameRepository: GameRepository, email: String) =
        gameRepository.updateGameStatus(gameUuid, email, status)
}