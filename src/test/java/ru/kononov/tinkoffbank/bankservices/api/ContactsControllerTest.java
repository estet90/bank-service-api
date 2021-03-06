package ru.kononov.tinkoffbank.bankservices.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kononov.tinkoffbank.bankservices.BankServicesApplication;
import ru.kononov.tinkoffbank.bankservices.api.entities.ApplicationDto;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.XML;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.TimeZone.getDefault;
import static javax.xml.bind.JAXBContext.newInstance;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * тесты для REST API
 *
 * @author dkononov
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BankServicesApplication.class})
@SpringBootTest(webEnvironment = RANDOM_PORT)
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

    @Before
    public void setup() {
        RestAssured.port = this.port;

        Contact contact = new Contact(CORRECT_CONTACT_ID);
        application = new Application(contact, PRODUCT_NAME);
        application.setApplicationId(APPLICATION_ID);

        when(applicationService.getLastProductByContactId(CORRECT_CONTACT_ID)).thenReturn(application);
        when(applicationService.getLastProductByContactId(INCORRECT_CONTACT_ID)).thenReturn(null);
    }

    @Test
    public void testOkJsonGetLastProductByContactId() throws IOException, BankServicesException {
        Response response = getResponse(correctPath(), JSON.withCharset(UTF_8));

        Application application = applicationFromJsonResponse(response);

        checkJsonResponse(response, SC_OK);
        assertEquals(this.application, application);
    }

    private Application applicationFromJsonResponse(Response response)
            throws IOException, BankServicesException {
        String responseString = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        // если не выставить явно параметр timeZone, десериализация проходит неправильно
        mapper.setTimeZone(getDefault());
        ApplicationDto applicationDto = mapper.readValue(responseString, ApplicationDto.class);
        return applicationDto.getApplication();
    }

    @Test
    public void testOkXmlGetLastProductByContactId() throws JAXBException, BankServicesException {
        Response response = getResponse(correctPath(), XML.withCharset(UTF_8));

        Application application = applicationFromXmlResponse(response);

        checkXmlResponse(response, SC_OK);
        assertEquals(this.application, application);
    }

    private Application applicationFromXmlResponse(Response response) throws JAXBException, BankServicesException {
        Unmarshaller unmarshaller = newInstance(ApplicationDto.class).createUnmarshaller();
        StringReader reader = new StringReader(response.asString());
        ApplicationDto applicationDto = (ApplicationDto) unmarshaller.unmarshal(reader);
        return applicationDto.getApplication();
    }

    @Test
    public void testNotFoundJsonGetLastProductByContactId() {
        Response response = getResponse(incorrectPath(), JSON.withCharset(UTF_8));

        checkJsonResponse(response, SC_NOT_FOUND);
    }

    @Test
    public void testNotFoundXmlGetLastProductByContactId() {
        Response response = getResponse(incorrectPath(), XML.withCharset(UTF_8));

        checkXmlResponse(response, SC_NOT_FOUND);
    }

    private String pathWithContactId(Long contactId) {
        return "/bank-services/api/contacts/" + contactId + "/applications/last";
    }

    private String correctPath() {
        return pathWithContactId(CORRECT_CONTACT_ID);
    }

    private String incorrectPath() {
        return pathWithContactId(INCORRECT_CONTACT_ID);
    }

    private Response getResponse(String path, String contentType) {
        return given().when().accept(contentType).get(path);
    }

    private void checkResponse(Response response, int status, String contentType) {
        response.then().statusCode(status).contentType(contentType);
    }

    private void checkJsonResponse(Response response, int status) {
        checkResponse(response, status, JSON.withCharset(UTF_8));
    }

    private void checkXmlResponse(Response response, int status) {
        checkResponse(response, status, XML.withCharset(UTF_8));
    }

}