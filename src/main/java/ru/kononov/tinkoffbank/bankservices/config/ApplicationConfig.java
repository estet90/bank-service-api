package ru.kononov.tinkoffbank.bankservices.config;

import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import ru.kononov.tinkoffbank.bankservices.api.ContactsController;

@Configuration
public class ApplicationConfig {

	@Autowired
	private Bus bus;

	@Autowired
	private ContactsController contactsController;

	@Autowired
	private HttpInterceptor httpInterceptor;

	@Bean
	public Server rsServer() {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setProvider(new JacksonJaxbJsonProvider());
		endpoint.setBus(bus);
		endpoint.setServiceBeans(Arrays.asList(contactsController));
		endpoint.setAddress("/api");
		endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
		endpoint.getBus().getOutInterceptors().add(httpInterceptor);
		return endpoint.create();
	}

}
