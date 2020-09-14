package com.numbergenerator;

import static springfox.documentation.builders.PathSelectors.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@ComponentScan(basePackages = { "com.numbergenerator", "com.numbergenerator.service.scheduler" })
public class NumberGeneratorApplication {

	private Logger logger = LoggerFactory.getLogger(NumberGeneratorApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(NumberGeneratorApplication.class, args);

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Initialize Swagger documentation
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public Docket productApi() {
		ApiInfo apiInfo = new ApiInfo("Number Generator API", "API to store and retrieve data from NumberGenerator Application", "1",
				"http://terms-of-service.url", "Vutukuri Sathvik", "License", "http://licenseurl");
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select().apis(RequestHandlerSelectors.any())
				.paths(regex("/api.*")).build().pathMapping("/").useDefaultResponseMessages(false);
	}
	
	
}
