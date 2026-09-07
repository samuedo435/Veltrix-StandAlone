package com.veltrix.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,

                                        new SecurityScheme()

                                                .name(securitySchemeName)

                                                .type(
                                                        SecurityScheme.Type.HTTP
                                                )

                                                .scheme("bearer")

                                                .bearerFormat("JWT")
                                )
                )

                .tags(List.of(
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Autenticación")
                                .description("Registro, inicio de sesión y consulta del usuario autenticado."),
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Categorías")
                                .description("Clasificación de los productos disponibles en la tienda."),
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Clientes")
                                .description("Gestión de la información de los clientes."),
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Productos")
                                .description("Catálogo de productos y sus existencias."),
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Pedidos")
                                .description("Consulta y gestión del ciclo de vida de los pedidos."),
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Pagos")
                                .description("Registro y consulta de pagos asociados a la tienda."),
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Detalles de pedido")
                                .description("Productos, cantidades y valores incluidos en cada pedido."),
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Usuarios")
                                .description("Administración de usuarios y roles del sistema.")
                ))

                .info(
                        new Info()

                                .title("Veltrix API")

                                .version("1.0")

                                .description("API REST de Veltrix para administrar una tienda online de calzado. "
                                        + "Permite gestionar el catálogo, clientes, usuarios, pedidos, detalles y pagos, "
                                        + "además de ofrecer autenticación basada en tokens JWT. "
                                        + "Las operaciones protegidas requieren enviar el token como "
                                        + "Authorization: Bearer <token>.")
                                .contact(new Contact()
                                        .name("Equipo Veltrix")
                                        .email("soporte@veltrix.com"))
                );
    }
}