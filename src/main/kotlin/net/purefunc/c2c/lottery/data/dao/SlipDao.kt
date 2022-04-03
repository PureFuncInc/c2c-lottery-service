package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.table.SlipDo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SlipDao : CoroutineCrudRepository<SlipDo, Long> {
}
