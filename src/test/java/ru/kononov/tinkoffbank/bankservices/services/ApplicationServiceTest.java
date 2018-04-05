package ru.kononov.tinkoffbank.bankservices.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

@RunWith(SpringRunner.class)
public class ApplicationServiceTest {

	@MockBean
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicationService applicationService;

	private Contact contact = new Contact();

	private Map<String, Application> applicationsMap = new HashMap<>(0);

	@TestConfiguration
	static class ApplicationServiceTestContextConfiguration {

		@Bean
		public ApplicationService applicationService() {
			return new ApplicationService();
		}
	}

	@Before
	public void setUp() throws Exception {
		fillData();
		Application last = contact.getApplications().stream()
				.max(Comparator.comparing(Application::getDateCreated).thenComparing(Application::getApplicationId)).get();
		Mockito.when(applicationRepository
				.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId()))
				.thenReturn(last);
	}

	private void fillData() {
		contact.setContactId(1L);
		Date currentDate = new Date();
		Arrays.stream(new String[] { "Кредит", "Ипотека", "Вклад" }).forEach(productName -> {
			Application application = new Application(contact, productName);
			application.setApplicationId(new Long(applicationsMap.size() + 1));
			application.setDateCreated(currentDate);
			applicationsMap.put(productName, application);
		});
		contact.setApplications(new ArrayList<>(applicationsMap.values()));
	}

	@Test
	public void testGetLastProductByContactId() {
		Application last = applicationService.getLastProductByContactId(contact.getContactId());
		assertEquals(applicationsMap.get("Вклад"), last);
	}

}
