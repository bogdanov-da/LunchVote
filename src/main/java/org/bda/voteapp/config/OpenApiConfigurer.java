package org.bda.voteapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Lunch Vote Application REST API doc",
                version = "1.0",
                description = "Lunch Vote Application"
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
public class OpenApiConfigurer {
    /*    @Bean
        public GroupedOpenApi api() {
                return GroupedOpenApi.builder()
                        .group("REST API")
                        .pathsToMatch("/api/**")
                        .build();
        }*/
}
