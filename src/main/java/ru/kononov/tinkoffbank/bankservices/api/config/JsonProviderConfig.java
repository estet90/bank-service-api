package ru.kononov.tinkoffbank.bankservices.api.config;

import javax.ws.rs.ext.Provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 *
 * @author dkononov
 *
 * подключение {@link JacksonJaxbJsonProvider}
 *
 */
@Provider
@Configuration
public class JsonProviderConfig {

	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJaxbJsonProvider();
	}

}
