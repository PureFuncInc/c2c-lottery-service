package net.purefunc.c2c.lottery.config

import net.purefunc.transmit.sdk.GmailClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Value("\${gmail.username}")
    private lateinit var username: String

    @Value("\${gmail.password}")
    private lateinit var password: String

    @Bean
    fun gmailClient() = GmailClient(username, password)
}