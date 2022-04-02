package net.purefunc.c2c.lottery.web.security

import net.purefunc.c2c.lottery.data.dao.reactive.MemberReactiveDao
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager(
    private val memberReactiveDao: MemberReactiveDao,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> =
        Mono.just(authentication)
            .map { JwtToken(it.credentials as String).retrieveSubject() }
            .flatMap { memberReactiveDao.findByEmail(it) }
            .map {
                UsernamePasswordAuthenticationToken(
                    it.email,
                    authentication.credentials as String,
                    listOf(SimpleGrantedAuthority(it.role))
                )
            }
}