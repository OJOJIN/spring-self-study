package com.study.jinyoung.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("오진영 API Document")
                .description("오진영 study server")
                .version("1.0.0");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }



}