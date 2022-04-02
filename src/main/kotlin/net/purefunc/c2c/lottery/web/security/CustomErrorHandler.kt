package net.purefunc.c2c.lottery.web.security

import net.purefunc.c2c.lottery.ext.randomUUID
import net.purefunc.core.ext.Slf4j
import net.purefunc.core.ext.Slf4j.Companion.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

// Either 專用
@Slf4j
class CustomErrorHandler {

    companion object {
        fun <T> process(tw: Throwable) = randomUUID()
            .also { log.error("$it -> " + tw.message, tw) }
            .run {
                val responseBodyBuilder = when (tw) {
                    is NoSuchElementException -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    is IllegalStateException -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    is IllegalArgumentException -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                }
                responseBodyBuilder.header("uuid", this).body(null)
            }
    }
}