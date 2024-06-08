package org.vm93.cinemille.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

	@Bean
	OpenAPI myOpenAPI() {

		Contact contact = new Contact();
		contact.setEmail("info@vincenzomaiorana.it");
		contact.setName("Vincenzo Maiorana");
		contact.setUrl("https://www.vincenzomaiorana.it");

		Info info = new Info().title("Cinemille API").version("1.0").contact(contact)
				.description("Multiplex CMS Backend");

		return new OpenAPI().info(info);
	}

}