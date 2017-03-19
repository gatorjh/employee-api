package com.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.service.Contact;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.employee.controller")
public class SwaggerConfiguration {
	public static final Contact DEFAULT_CONTACT = new Contact(null, null, null);
	@Autowired
	Environment env;

	@Bean
	public Docket identityApi() {
        ApiInfo apiInfo = new ApiInfo(
                env.getProperty("info.app.name"),
                env.getProperty("info.app.description"),
                env.getProperty("info.version"), null, DEFAULT_CONTACT, null, null);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("employee-api")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/rest/.*"))
                .build();
	}

}