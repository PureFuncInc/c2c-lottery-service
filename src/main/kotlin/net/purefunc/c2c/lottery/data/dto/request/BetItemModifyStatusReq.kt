package net.purefunc.c2c.lottery.data.dto.request

import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import net.purefunc.c2c.lottery.data.repository.GameRepository

data class BetItemModifyStatusReq(

    val betItemUuids: List<String>,

    val status: BetItemStatus,
) {

    suspend fun modifyBetItems(gameRepository: GameRepository, email: String) =
        gameRepository.updateBetItemsStatus(betItemUuids, email, status)
}