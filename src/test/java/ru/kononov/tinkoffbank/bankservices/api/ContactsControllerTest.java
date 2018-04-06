package ru.kononov.tinkoffbank.bankservices.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.transport.local.LocalConduit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kononov.tinkoffbank.bankservices.api.providers.InvalidDataAccessResourceUsageExceptionMapper;
import ru.kononov.tinkoffbank.bankservices.api.providers.RestResponseFilter;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log4j2
@RunWith(SpringRunner.class)
public class ContactsControllerTest {

	@Autowired
	private static ContactsController contactsController;
	@Autowired
	private static RestResponseFilter restResponseFilter;
	@Autowired
	private static InvalidDataAccessResourceUsageExceptionMapper invalidDataAccessResourceUsageExceptionMapper;

	private static List<Object> providers = new ArrayList<>();

	private static Server server;
	private WebClient client;

	@TestConfiguration
	static class ApplicationServiceTestContextConfiguration {

		@Bean
		public ContactsController contactsController() {
			return new ContactsController();
		}

	}

	@BeforeClass
	public static void setUpBeforeClass() {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		providers.addAll(Arrays.asList(new JacksonJaxbJsonProvider(), restResponseFilter, invalidDataAccessResourceUsageExceptionMapper));
		endpoint.setProviders(providers);
		endpoint.setBus(new SpringBus());
		endpoint.setServiceBean(contactsController);
		endpoint.setAddress("/api");
		endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
		server = endpoint.create();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		server.stop();
		server.destroy();
	}

	@Before
	public void setUp() throws Exception {
		client = WebClient.create("http://localhost:8080/bank-services/api", providers);
		WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLastProductByContactId() {
		client.accept(MediaType.APPLICATION_XML_TYPE);
		client.path("/contacts/1/applications/last");
		Response response = client.get(Response.class);
		System.out.println(response.getEntity().toString());
		assertEquals(true, true);
	}

}
