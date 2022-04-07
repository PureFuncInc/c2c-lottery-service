package net.purefunc.c2c.lottery.web.security

import net.purefunc.core.ext.Slf4j
import net.purefunc.core.ext.Slf4j.Companion.log
import net.purefunc.core.ext.randomUUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// 無法把 Flux or Flow 包在 Either<Throwable, B>，只能這樣抓 Exception
@Slf4j
@RestControllerAdvice
class CustomRestControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception) = randomUUID()
        .also { log.error("$it -> ${ex.message}", ex) }
        .run { ResponseEntity.internalServerError().body(this) }
}