package net.purefunc.c2c.lottery.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        jwtAuthenticationManager: ReactiveAuthenticationManager,
        jwtAuthenticationConverter: ServerAuthenticationConverter,
    ) = run {
        val authenticationWebFilter = AuthenticationWebFilter(jwtAuthenticationManager)
        authenticationWebFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)

        http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .logout().disable()

            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)

            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .pathMatchers("/swagger-ui.html", "/webjars/**", "/v3/api-docs", "/v3/api-docs/swagger-config", "/api-docs")
            .permitAll()
            .pathMatchers(HttpMethod.GET, "/api/v1.0/auth").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/v1.0/auth").permitAll()
            .pathMatchers(HttpMethod.GET, "/api/v1.0/wallet").permitAll()
            .pathMatchers("/**").authenticated()

            .and().build()
    }
}
