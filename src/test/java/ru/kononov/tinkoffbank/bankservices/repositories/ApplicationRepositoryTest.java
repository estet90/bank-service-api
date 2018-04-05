package ru.kononov.tinkoffbank.bankservices.repositories;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ApplicationRepository applicationRepository;

	private Contact contact = new Contact();

	private Map<String, Application> applications = new HashMap<>();

	@Before
	public void setUp() throws Exception {
		contact = entityManager.persist(contact);
		Arrays.stream(new String[] { "Кредит", "Ипотека", "Вклад" }).forEach(productName -> {
			Application application = entityManager.persist(new Application(contact, productName));
			applications.put(productName, application);
		});
	}

	@After
	public void tearDown() throws Exception {
		applications.entrySet().forEach(entry -> {
			entityManager.remove(entry.getValue());
		});
		entityManager.remove(contact);
	}

	@Test
	public void testFindTopByContactContactIdOrderByDateCreatedDesc() {
		Application last = applicationRepository
				.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId());
		assertEquals(applications.get("Вклад"), last);
	}

}
