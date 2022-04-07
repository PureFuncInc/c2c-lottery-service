package net.purefunc.c2c.lottery.data.dto.request

import arrow.core.Either.Companion.catch
import net.purefunc.c2c.lottery.data.repository.GameRepository
import java.math.BigDecimal

data class BetItemModifyOddsReq(

    val betItemUuids: List<String>,

    val odds: List<BigDecimal>,
) {
    fun modify(gameRepository: GameRepository, name: String) =
        catch {

        }
}