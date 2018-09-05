package ru.kononov.tinkoffbank.bankservices.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * интеграционный тест для {@link ApplicationRepository}
 *
 * @author dkononov
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Contact contact = new Contact();

    private List<Application> applicationsList = new ArrayList<>();

    @Before
    public void setUp() {
        contact = entityManager.persist(contact);
        Arrays.stream(new String[]{"Кредит", "Ипотека", "Вклад"}).forEach(productName -> {
            Application application = entityManager.persist(new Application(contact, productName));
            applicationsList.add(application);
        });
    }

    @After
    public void tearDown() {
        applicationsList.stream().forEach(application -> {
            entityManager.remove(application);
        });
        entityManager.remove(contact);
    }

    @Test
    public void testFindTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc() {
        Application last = applicationRepository
                .findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId());
        Application lastinList = applicationsList.stream()
                .max(Comparator.comparing(Application::getDateCreated).thenComparing(Application::getApplicationId)).get();
        assertEquals(lastinList, last);
    }

}
