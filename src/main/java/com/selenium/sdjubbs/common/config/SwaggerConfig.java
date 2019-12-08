package com.selenium.sdjubbs.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SDJUBBS API")
                .contact(new Contact("Selenium", "https://www.github.com/wantao666", "selenium39@qq.com"))
                .description("API FOR SDJUBBS")
                .version("1.0.0")
                .termsOfServiceUrl("https://www.github.com/wantao666")
                .build();
    }

    @Bean
    public Docket createResApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.selenium.sdjubbs.common.controller"))
                .paths(PathSelectors.any())
                .build();

    }
}
