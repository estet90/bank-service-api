package ru.kononov.tinkoffbank.bankservices.services;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

/**
 * 
 * тест для {@link ApplicationService}
 * 
 * @author dkononov
 *
 */
@RunWith(SpringRunner.class)
public class ApplicationServiceTest {

	@MockBean
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicationService applicationService;

	private Contact contact = new Contact(1L);

	private List<Application> applicationsList = new LinkedList<>();

	@TestConfiguration
	static class ApplicationServiceTestContextConfiguration {

		@Bean
		public ApplicationService applicationService() {
			return new ApplicationService();
		}

	}

	@Before
	public void setUp() {
		fillData();
		Application last = ((LinkedList<Application>)applicationsList).getLast();
		Mockito.when(applicationRepository
				.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId()))
				.thenReturn(last);
	}

	private void fillData() {
		Date currentDate = new Date();
		Arrays.stream(new String[] { "Кредит", "Ипотека", "Вклад" }).forEach(productName -> {
			Application application = new Application(contact, productName);
			application.setApplicationId((long) (applicationsList.size() + 1));
			application.setDateCreated(currentDate);
			applicationsList.add(application);
		});
	}

	@Test
	public void testGetLastProductByContactId() {
		Application last = applicationService.getLastProductByContactId(contact.getContactId());
		assertEquals(((LinkedList<Application>) applicationsList).getLast(), last);
	}

}
