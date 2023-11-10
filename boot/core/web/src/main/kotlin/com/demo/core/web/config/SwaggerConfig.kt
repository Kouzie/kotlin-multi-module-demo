package com.demo.core.web.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Value(value = "\${spring.profiles.active}")
    val profile: String = "local"

    @Bean
    fun openAPI(): OpenAPI {
        val baseurl: String =
            if (profile == "dev") "https://beflyer.beyless.com/edge-platform" else "http://localhost:8080"
        val serverProfile: Server = Server()
            .description(profile)
            .url(baseurl);
        val info: Info = Info()
            .title(String.format("EDGE-PLATFORM API SERVICE(%s)", profile))
            .description(String.format("EDGE-PLATFORM API SERVICE(%s)", profile))
            .contact(
                Contact().name(String.format("EDGE-PLATFORM API SERVICE(%s)", profile))
                    .url(baseurl)
                    .email("edge-platform@beyless.com")
            )
            .version(profile);
        return OpenAPI()
            .components(Components())
            .info(info)
            .components(
                Components().addSecuritySchemes(
                    "bearerAuth", SecurityScheme()
                        .name("bearerAuth")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .servers(listOf(serverProfile))
        ;
    }
}
