package com.dt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


//https://www.javaguides.net/2023/03/spring-boot-3-rest-api-documentation.html
@Configuration
public class DocConfiguration {

	/*
	 * @Bean GroupedOpenApi data() { return
	 * GroupedOpenApi.builder().group(DocConstant.TAG_DATA)
	 * .pathsToMatch(DocConstant.API_VERSION + "/" +
	 * DocConstant.TAG_DATA_URL).build();
	 * 
	 * }
	 */

	@Bean
	OpenAPI info() {
		return new OpenAPI()
				.info(new Info().title("Datatable Spring Boot Test").description("Datatable Test using Spring Boot")
						.version("v0.0.1").license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("DTGrid Documentation")
						.url("https://springshop.wiki.github.org/docs"));
	}
}
