package ru.kononov.tinkoffbank.bankservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

/**
 *
 * обвязка над {@link ApplicationRepository}
 * 
 * @author dkononov
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
		return applicationRepository.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contactId);
	}

}
