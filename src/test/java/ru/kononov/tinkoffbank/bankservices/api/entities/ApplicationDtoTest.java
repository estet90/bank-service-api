package ru.kononov.tinkoffbank.bankservices.api.entities;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * тесты выполнения преобразований {@link Application} <-> {@link ApplicationDto}
 *
 * @author dkononov
 */
@Log4j2
public class ApplicationDtoTest {

    private Application application;

    @Before
    public void setUp() {
        Contact contact = new Contact(1L);
        application = new Application(1L, contact, "Кредит", new Date());
    }

    @Test
    public void testGetApplication() {
        try {
            ApplicationDto applicationDto = new ApplicationDto(application);
            assertEquals(application, applicationDto.getApplication());
        } catch (BankServicesException e) {
            log.error(e);
        }
    }

    @Test
    public void testExceptionCreateApplicationDtoFromEmptyApplication() {
        try {
            new ApplicationDto(null);
        } catch (BankServicesException e) {
            assertThat(e.getMessage(), is(BankServicesException.MESSAGE_TRANSMITTED_EMPTY_APPLICATION));
        }
    }

    @Test
    public void testExceptionCreateApplicationDtoWithEmptyContact() {
        try {
            Application application = new Application();
            application.setApplicationId(1L);
            new ApplicationDto(application);
        } catch (BankServicesException e) {
            assertThat(e.getMessage(), is(BankServicesException.MESSAGE_CONTACT_IS_NOT_TIED));
        }
    }

    @Test
    public void testExceptionCreateApplicationFromDtoWithEmptyApplication() {
        try {
            new ApplicationDto().getApplication();
        } catch (BankServicesException e) {
            assertThat(e.getMessage(), is(BankServicesException.MESSAGE_RECEIVED_EMPTY_APPLICATION));
        }
    }
}