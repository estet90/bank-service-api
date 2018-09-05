package ru.kononov.tinkoffbank.bankservices.api.entities;

import org.junit.Test;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException.MESSAGE_CONTACT_IS_NOT_TIED;
import static ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException.MESSAGE_RECEIVED_EMPTY_APPLICATION;
import static ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException.MESSAGE_TRANSMITTED_EMPTY_APPLICATION;

/**
 * тесты выполнения преобразований {@link Application} <-> {@link ApplicationDto}
 *
 * @author dkononov
 */
public class ApplicationDtoTest {

    @Test
    public void testGetApplication() throws BankServicesException {
        Contact contact = new Contact(1L);
        Application application = new Application(1L, contact, "Кредит", new Date());

        ApplicationDto applicationDto = new ApplicationDto(application);

        assertEquals(application, applicationDto.getApplication());
    }

    @Test
    public void testExceptionCreateApplicationDtoFromEmptyApplication() {
        try {
            new ApplicationDto(null);
        } catch (BankServicesException e) {
            assertThat(e.getMessage(), is(MESSAGE_TRANSMITTED_EMPTY_APPLICATION));
        }
    }

    @Test
    public void testExceptionCreateApplicationDtoWithEmptyContact() {
        try {
            Application application = new Application();
            application.setApplicationId(1L);

            new ApplicationDto(application);
        } catch (BankServicesException e) {
            assertThat(e.getMessage(), is(MESSAGE_CONTACT_IS_NOT_TIED));
        }
    }

    @Test
    public void testExceptionCreateApplicationFromDtoWithEmptyApplication() {
        try {
            new ApplicationDto().getApplication();
        } catch (BankServicesException e) {
            assertThat(e.getMessage(), is(MESSAGE_RECEIVED_EMPTY_APPLICATION));
        }
    }
}