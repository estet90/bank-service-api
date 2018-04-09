package ru.kononov.tinkoffbank.bankservices.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import ru.kononov.tinkoffbank.bankservices.BankServicesApplication;
import ru.kononov.tinkoffbank.bankservices.api.entities.ApplicationDto;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

/**
 * 
 * тесты для REST API
 * 
 * @author dkononov
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BankServicesApplication.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Log4j2
public class ContactsControllerTest {

	@LocalServerPort
	private int port;

	@MockBean
	private ApplicationService applicationService;

	private Application application;

	private static final long APPLICATION_ID = 1L;

	private static final String PRODUCT_NAME = "Кредит";

	private static final long CORRECT_CONTACT_ID = 1L;
	private static final long INCORRECT_CONTACT_ID = 2L;

	private static final String CONTENT_TYPE_JSON = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
	private static final String CONTENT_TYPE_XML = ContentType.XML.withCharset(StandardCharsets.UTF_8);

	@Before
	public void setup() {
		RestAssured.port = this.port;

		Contact contact = new Contact(CORRECT_CONTACT_ID);
		application = new Application(contact, PRODUCT_NAME);
		application.setApplicationId(APPLICATION_ID);

		Mockito.when(applicationService.getLastProductByContactId(CORRECT_CONTACT_ID)).thenReturn(application);
		Mockito.when(applicationService.getLastProductByContactId(INCORRECT_CONTACT_ID)).thenReturn(null);
	}

	@Test
	public void testOkJsonGetLastProductByContactId() {
		Response response = getResponse(correctPath(), CONTENT_TYPE_JSON);
		response.then().statusCode(HttpStatus.SC_OK).contentType(CONTENT_TYPE_JSON);

		Application application = null;
		try {
			application = applicationFromJsonResponse(response);
		} catch (IOException | BankServicesException e) {
			log.error(e);
		}
		assertEquals(this.application, application);
	}

	private Application applicationFromJsonResponse(Response response)
			throws JsonParseException, JsonMappingException, IOException, BankServicesException {
		String responseString = response.asString();
		ObjectMapper mapper = new ObjectMapper();
		// если не выставить явно параметр timeZone, десериализация проходит неправильно
		mapper.setTimeZone(TimeZone.getDefault());
		ApplicationDto applicationDto = mapper.readValue(responseString, ApplicationDto.class);
		Application application = applicationDto.getApplication();
		return application;
	}

	@Test
	public void testOkXmlGetLastProductByContactId() throws JAXBException, BankServicesException {
		Response response = getResponse(correctPath(), CONTENT_TYPE_XML);
		response.then().statusCode(HttpStatus.SC_OK).contentType(CONTENT_TYPE_XML);

		Application application = null;
		try {
			application = applicationFromXmlResponse(response);
		} catch (JAXBException | BankServicesException e) {
			log.error(e);
		}
		assertEquals(this.application, application);
	}

	private Application applicationFromXmlResponse(Response response) throws JAXBException, BankServicesException {
		JAXBContext jc = JAXBContext.newInstance(ApplicationDto.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		String responseString = response.asString();
		StringReader reader = new StringReader(responseString);
		ApplicationDto applicationDto = (ApplicationDto) unmarshaller.unmarshal(reader);
		Application application = applicationDto.getApplication();
		return application;
	}

	@Test
	public void testNotFoundJsonGetLastProductByContactId() {
		Response response = getResponse(incorrectPath(), CONTENT_TYPE_JSON);
		response.then().statusCode(HttpStatus.SC_NOT_FOUND).contentType(CONTENT_TYPE_JSON);
	}
	
	@Test
	public void testNotFoundXmlGetLastProductByContactId() {
		Response response = getResponse(incorrectPath(), CONTENT_TYPE_XML);
		response.then().statusCode(HttpStatus.SC_NOT_FOUND).contentType(CONTENT_TYPE_XML);
	}

	private String pathWithContactId(Long contactId) {
		return new StringBuilder("/bank-services/api/contacts/").append(contactId).append("/applications/last")
				.toString();
	}

	private String correctPath() {
		return pathWithContactId(CORRECT_CONTACT_ID);
	}

	private String incorrectPath() {
		return pathWithContactId(INCORRECT_CONTACT_ID);
	}

	private Response getResponse(String path, String contentType) {
		Response response = RestAssured.given().when().accept(contentType).get(path);
		return response;
	}

}