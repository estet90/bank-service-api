package ru.kononov.tinkoffbank.bankservices.api;

import java.nio.charset.Charset;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import ru.kononov.tinkoffbank.bankservices.BankServicesApplication;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BankServicesApplication.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactsControllerTest {

	@LocalServerPort
	private int port;

	@MockBean
	private ApplicationService applicationService;

	private long correctContactId = 1L;
	private long incorrectContactId = 2L;

	@Before
	public void setup() {
		RestAssured.port = this.port;

		Contact contact = new Contact(correctContactId);
		Application application = new Application(contact, "Кредит");
		application.setApplicationId(1L);

		Mockito.when(applicationService.getLastProductByContactId(correctContactId)).thenReturn(application);
		Mockito.when(applicationService.getLastProductByContactId(incorrectContactId)).thenReturn(null);
	}

	@Test
	public void testOkGetLastProductByContactId() {
		RestAssured.given()
				.when().accept(ContentType.JSON).get(pathWithContactId(correctContactId))
				.then().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON);
	}

	@Test
	public void testRedirectGetLastProductByContactId() {
		RestAssured.given()
				.when().accept(ContentType.JSON).get(pathWithContactId(incorrectContactId))
				.then().statusCode(HttpStatus.SC_OK).contentType(ContentType.HTML.withCharset(Charset.defaultCharset()));
	}

	private String pathWithContactId(Long contactId) {
		return new StringBuilder("/bank-services/api/contacts/")
				.append(contactId)
				.append("/applications/last")
				.toString();
	}

}