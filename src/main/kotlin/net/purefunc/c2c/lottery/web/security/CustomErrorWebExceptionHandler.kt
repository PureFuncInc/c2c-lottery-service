package net.purefunc.c2c.lottery.web.security

import io.jsonwebtoken.ExpiredJwtException
import net.purefunc.core.ext.Slf4j
import net.purefunc.core.ext.Slf4j.Companion.log
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

// 抓 JWT 錯誤專用, 因為 RestControllerAdvice 抓不到
// JWT 過期或是沒有對應帳號的時候，應該跟他說 401 (不加這個會回500)
@Slf4j
@Component
class CustomErrorWebExceptionHandler : ErrorWebExceptionHandler {

    override fun handle(
        exchange: ServerWebExchange,
        tw: Throwable,
    ): Mono<Void> = exchange.response
        .also { log.error(tw.message, tw) }
        .run {
            when {
                // Jwt 過期
                tw is ExpiredJwtException -> this.statusCode = HttpStatus.UNAUTHORIZED
                // 找不到對應的帳號
                tw is IllegalStateException && tw.message!!.contains("UsernamePasswordAuthenticationToken") ->
                    this.statusCode = HttpStatus.UNAUTHORIZED
                else -> this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
            }
        }
        .let { Mono.empty() }
}