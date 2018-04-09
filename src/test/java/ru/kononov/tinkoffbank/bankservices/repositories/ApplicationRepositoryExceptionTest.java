package ru.kononov.tinkoffbank.bankservices.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ru.kononov.tinkoffbank.bankservices.entities.Contact;

/**
 * 
 * тест для получения исключения при работе с БД
 * 
 * @author dkononov
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = "spring.datasource.platform=null")
public class ApplicationRepositoryExceptionTest {

	@Autowired
	private ApplicationRepository applicationRepository;

	private Contact contact = new Contact();

	@Test(expected = InvalidDataAccessResourceUsageException.class)
	public void testExceptionFindTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc()
			throws InvalidDataAccessResourceUsageException {
		applicationRepository.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contact.getContactId());
	}

}
