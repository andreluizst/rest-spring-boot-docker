package br.com.restsb.config;


import java.util.Collections;
import java.util.function.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Contact;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.apis(RequestHandlerSelectors.any())
				.apis(apis())//filtrando o que será documentado
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("RESTful API with Spring Boot",
				"Primeira API com HATEOAS e Swagger",
				"V1", "Terms of service Url",
				new Contact("André Luiz Silva Teotônio", "site", "andre.luiz.silvat@gmail.com"),
				"License of API",
				"License URL", Collections.emptyList());
	}

	private Predicate<RequestHandler> apis(){
		return RequestHandlerSelectors.basePackage("br.com.restsb.controller.person");
	}
}
