package net.purefunc.c2c.lottery.web.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
        Mono.just(exchange)
            .flatMap { Mono.justOrEmpty(it.request.headers.getFirst(HttpHeaders.AUTHORIZATION)) }
            .filter { it.isNotEmpty() }
            .map {
                UsernamePasswordAuthenticationToken(
                    it.substring(7),
                    it.substring(7),
                )
            }
}