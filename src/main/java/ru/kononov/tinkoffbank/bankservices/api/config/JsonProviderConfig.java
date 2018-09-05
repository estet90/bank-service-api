package ru.kononov.tinkoffbank.bankservices.api.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.Provider;

/**
 * подключение {@link JacksonJaxbJsonProvider}
 *
 * @author dkononov
 */
@Provider
@Configuration
public class JsonProviderConfig {

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJaxbJsonProvider();
    }

}
