package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.table.BetItemDo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BetItemDao : CoroutineCrudRepository<BetItemDo, Long> {
}
