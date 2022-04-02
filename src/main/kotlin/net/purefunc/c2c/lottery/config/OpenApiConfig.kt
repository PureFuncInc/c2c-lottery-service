package net.purefunc.c2c.lottery.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.annotations.servers.ServerVariable
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(title = "C2C Lottery Service API", version = "0.0.1"),
    servers = [
        Server(
            url = "{schema}://localhost:8080/c2c-lottery-service",
            variables = [
                ServerVariable(
                    name = "schema",
                    allowableValues = ["http"],
                    defaultValue = "http",
                )
            ],
            description = "ide",
        ),
//        Server(
//            url = "{schema}://twshop.tech/ticket-service",
//            variables = [
//                ServerVariable(
//                    name = "schema",
//                    allowableValues = ["https"],
//                    defaultValue = "https",
//                ),
//            ],
//            description = "stage-env",
//        ),
    ],
)
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class OpenApiConfig {
}
