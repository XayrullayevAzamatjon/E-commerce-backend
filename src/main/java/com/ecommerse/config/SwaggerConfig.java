package com.ecommerse.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Azamatjon",
                email = "xayrullayevazamatjon5@gmail.com",
                url = "https://google.com"
        ),
        description = "OpenApi documentation for E-commerce Application",
        title = "OpenApi specification - Azamatjon",
        version = "1.0",
        license = @License(
                name = "Licence name",
                url = "https://some-url.com"
        ),
        termsOfService = "Terms of service"
),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://aliboucoding.com/course"
                )
        }/*,
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }*/)
/*@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)*/
public class SwaggerConfig {
}
