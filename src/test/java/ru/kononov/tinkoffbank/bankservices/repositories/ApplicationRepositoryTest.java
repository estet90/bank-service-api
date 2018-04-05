package ru.kononov.tinkoffbank.bankservices.repositories;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

	private List<Application> applicationsList = new LinkedList<>();

	@Before
	public void setUp() throws Exception {
		contact = entityManager.persist(contact);
		Arrays.stream(new String[] { "Кредит", "Ипотека", "Вклад" }).forEach(productName -> {
			Application application = entityManager.persist(new Application(contact, productName));
			applicationsList.add(application);
		});
	}

	@After
	public void tearDown() throws Exception {
		applicationsList.stream().forEach(application -> {
			entityManager.remove(application);
		});
		entityManager.remove(contact);
	}

	@Test
	public void testFindTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc() {
		Application last = applicationRepository
				.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId());
		assertEquals(((LinkedList<Application>)applicationsList).getLast(), last);
	}

}
