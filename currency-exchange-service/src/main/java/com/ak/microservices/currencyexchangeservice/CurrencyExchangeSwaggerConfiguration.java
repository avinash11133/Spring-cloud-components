package com.ak.microservices.currencyexchangeservice;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class CurrencyExchangeSwaggerConfiguration {
	
	@Bean
	public Docket exchangeApi()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ak.microservices.currencyexchangeservice"))
                .paths(PathSelectors.regex("/currency-exchange.*"))
                .build()
                .apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo()
	{
		return new ApiInfo(
                "CurrencyExchangeService",
                "Spring Boot Swagger API for SIRIUSXM",
                "1.0",
                "Terms of Service",
                new Contact("SXM", "https://www.siriusxm.com/",
                        "A.KUMAR@BIGSPACESHIP.COM"),
                "License Version 2.0",
                "https://www.BSS.org/licesen.html", Collections.emptyList()
        );
        
           
	}
	
}
