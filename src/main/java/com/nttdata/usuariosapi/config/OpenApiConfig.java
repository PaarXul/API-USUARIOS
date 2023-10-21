package com.nttdata.usuariosapi.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
            contact = @Contact(
                name = "Gian Parra",
                email = "gian.parra96@gmail.com",
                url = "https://github.com/PaarXul/"
            ),
            description = "API para el manejo de usuarios",
            title = "Usuarios API ",
            version = "1.0.0"
),
        servers = {
                @Server(
                        description = "Servidor local",
                        url = "http://localhost:8081"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {



}
