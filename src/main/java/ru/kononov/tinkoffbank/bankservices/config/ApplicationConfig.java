package ru.kononov.tinkoffbank.bankservices.config;

import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.kononov.tinkoffbank.bankservices.api.ContactsController;

@Configuration
public class ApplicationConfig {

	@Autowired
	private Bus bus;

	@Bean
	public Server server() {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setServiceBeans(Arrays.<Object>asList(new ContactsController()));
		endpoint.setAddress("/api");
		return endpoint.create();
	}

}
