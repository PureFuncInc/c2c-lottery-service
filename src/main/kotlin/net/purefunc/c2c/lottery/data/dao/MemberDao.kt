package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.table.MemberDo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface MemberDao : CoroutineCrudRepository<MemberDo, Long> {

    suspend fun findByEmail(email: String): MemberDo?
}
