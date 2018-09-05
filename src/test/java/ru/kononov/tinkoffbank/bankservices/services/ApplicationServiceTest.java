package ru.kononov.tinkoffbank.bankservices.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * тест для {@link ApplicationService}
 *
 * @author dkononov
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        ApplicationService.class
})
public class ApplicationServiceTest {

    @MockBean
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void testGetLastProductByContactId() {
        Contact contact = new Contact(1L);
        Application application = givenApplication(contact);
        when(applicationRepository
                .findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId()))
                .thenReturn(application);

        Application whenApplication = applicationService.getLastProductByContactId(contact.getContactId());

        thenApplication(application, whenApplication);
    }

    private Application givenApplication(Contact contact) {
        Application application = new Application();
        application.setApplicationId(1L);
        application.setDateCreated(new Date());
        application.setContact(contact);
        application.setProductName("кредит");
        return application;
    }

    private void thenApplication(Application application, Application whenApplication) {
        assertEquals(application.getApplicationId(), whenApplication.getApplicationId());
        assertEquals(application.getDateCreated(), whenApplication.getDateCreated());
        assertEquals(application.getProductName(), whenApplication.getProductName());
        assertEquals(application.getContact().getContactId(), whenApplication.getContact().getContactId());
    }

}
