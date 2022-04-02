package net.purefunc.c2c.lottery.ext

import arrow.core.Either
import net.purefunc.c2c.lottery.web.security.CustomErrorHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T> Either<Throwable, T>.returnToken() =
    fold(
        ifLeft = { tw -> CustomErrorHandler.process(tw) },
        ifRight = { ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer $it").body("") },
    )

fun <T> Either<Throwable, T>.return200() =
    fold(
        ifLeft = { tw -> CustomErrorHandler.process(tw) },
        ifRight = { ResponseEntity<T>(it, HttpStatus.OK) },
    )

fun <T> Either<Throwable, T>.return202() =
    fold(
        ifLeft = { tw -> CustomErrorHandler.process(tw) },
        ifRight = { ResponseEntity<T>(it, HttpStatus.ACCEPTED) },
    )

fun <T> Either<Throwable, T>.return204() =
    fold(
        ifLeft = { tw -> CustomErrorHandler.process(tw) },
        ifRight = { ResponseEntity<T>(it, HttpStatus.NO_CONTENT) },
    )