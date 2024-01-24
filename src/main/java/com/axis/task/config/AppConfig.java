package com.axis.task.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AppConfig {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("axis-task")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.axis.task"))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Axis Task")
                .description("Axis Task API")
                .contact(new Contact("Youssef", "https://www.linkedin.com/in/youssef-ibrahem-dev/","youssef.ibrahim.salama.mail@gmail.com"))
                .version("1.0")
                .build();
    }
}
