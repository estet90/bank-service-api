package ru.kononov.tinkoffbank.bankservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

@Service
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	public Application getLastProductByContactId(Long contactId) {
		return applicationRepository.findTopByContactContactIdOrderByDateCreatedDesc(contactId);
	}

}
