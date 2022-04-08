package net.purefunc.c2c.lottery.data.dto.request

import net.purefunc.c2c.lottery.data.repository.GameRepository
import java.math.BigDecimal

data class BetItemModifyOddsReq(

    val betItemUuids: List<String>,

    val odds: List<BigDecimal>,
) {

    suspend fun modify(gameRepository: GameRepository, email: String) =
        gameRepository.updateBetItemsOdds(betItemUuids, email, odds)
}