package net.purefunc.c2c.lottery.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class CorsConfig : WebFluxConfigurer {

    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("*")
            .maxAge(-1)
    }
}