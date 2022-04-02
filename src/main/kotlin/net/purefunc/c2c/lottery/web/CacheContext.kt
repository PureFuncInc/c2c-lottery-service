package net.purefunc.c2c.lottery.web

import com.github.benmanes.caffeine.cache.Caffeine
import net.purefunc.c2c.lottery.data.table.MemberDo
import net.purefunc.core.ext.Slf4j
import org.springframework.stereotype.Component
import java.time.Duration

@Slf4j
@Component
class CacheContext {

    val tokenToMember = Caffeine.newBuilder()
        .initialCapacity(1024)
        .expireAfterAccess(Duration.ofSeconds(300))
        .build<String, MemberDo>()
}