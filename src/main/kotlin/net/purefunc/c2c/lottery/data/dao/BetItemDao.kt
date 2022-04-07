package net.purefunc.c2c.lottery.data.dao

import kotlinx.coroutines.flow.Flow
import net.purefunc.c2c.lottery.data.table.BetItemDo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BetItemDao : CoroutineCrudRepository<BetItemDo, Long> {

    suspend fun findByUuid(uuid: String): BetItemDo?

    fun findAllByGameId(gameId: Long): Flow<BetItemDo>
}
