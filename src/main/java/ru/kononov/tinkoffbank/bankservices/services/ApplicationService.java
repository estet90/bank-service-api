package ru.kononov.tinkoffbank.bankservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

/**
 * 
 * @author dkononov
 * 
 * обвязка над {@link ApplicationRepository}
 *
 */
@Service
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	/**
	 * 
	 * получение последней заявки по идентификатору контакта
	 * 
	 * @param contactId
	 * @return заявка
	 */
	public Application getLastProductByContactId(Long contactId) {
		return applicationRepository.findTopByContactContactIdOrderByDateCreatedDesc(contactId);
	}

}
