package com.Itauchallenge.transaction.statistics.openapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.openapi("3.0.1")
			.info(new Info()
				.title("Itau challenge with the objective of receiving transactions and returning detailed statistics")
				.version("v1")
				.description("Challenge proposed by Itaú with a focus on processing transactions and providing detailed statistics in real time.")
				.termsOfService("https://www.itauchallenge.com/services")
				.license(
					new License()
						.name("Apache 2.0")
						.url("https://www.apache.org/licenses/LICENSE-2.0")
					)
				);
		
	}
}
