package com.ak.microservices.springcloudgateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Configuration
public class SpringCloudGatewayConfig {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		System.out.println("inside spring cloud gateway's RoueLocator bean");
    	return builder.routes()
    	        .route("currency-exchange-service",p -> p.path("/currency-exchange/from/**")
    	            .filters(f -> f.filter((exchange, chain) -> {
    	            	log.info("Routing through API gateway");
    	            	return chain.filter(exchange);
    	            }))
    	            .uri("lb://currency-exchange-service"))
    	        .build();
    }
	
}
