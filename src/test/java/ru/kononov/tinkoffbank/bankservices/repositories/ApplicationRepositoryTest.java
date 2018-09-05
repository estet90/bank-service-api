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
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
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
        applicationsList = Stream.of("Кредит", "Ипотека", "Вклад")
                .map(productName -> entityManager.persist(new Application(contact, productName)))
                .collect(toList());
    }

    @After
    public void tearDown() {
        applicationsList.forEach(application -> entityManager.remove(application));
        entityManager.remove(contact);
    }

    @Test
    public void testFindTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc() {
        Application lastInList = applicationsList.stream()
                .max(comparing(Application::getDateCreated)
                        .thenComparing(Application::getApplicationId))
                .orElse(null);

        Application last = applicationRepository.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId());

        assertEquals(lastInList, last);
    }

}
