package net.purefunc.c2c.lottery.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@EnableR2dbcRepositories(basePackages = ["net.purefunc.c2c.lottery.data.dao"])
class AppConfig {
}