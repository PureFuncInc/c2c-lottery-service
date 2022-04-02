package net.purefunc.c2c.lottery.data.dao.reactive

import net.purefunc.c2c.lottery.data.table.MemberDo
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Mono

interface MemberReactiveDao : ReactiveSortingRepository<MemberDo, Long> {

    fun findByEmail(email: String): Mono<MemberDo>
}