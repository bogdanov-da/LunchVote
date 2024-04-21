package org.bda.voteapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Lunch Vote Application REST API doc",
                version = "1.0",
                description = "admin - admin@gmail.com/admin_password, user - user@gmail.com/user_password"
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
public class LunchVoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(LunchVoteApplication.class, args);
    }

}
