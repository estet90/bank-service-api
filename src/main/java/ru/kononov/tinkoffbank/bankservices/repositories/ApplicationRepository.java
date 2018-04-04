package ru.kononov.tinkoffbank.bankservices.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ru.kononov.tinkoffbank.bankservices.entities.Application;

/**
 * 
 * @author dkononov
 * 
 * репозиторий для работы с сущностью заявка
 *
 */
@Repository
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {

	/**
	 * 
	 * получение последней заявки по идентификатору контакта
	 * 
	 * @param contactId
	 * @return заявка
	 */
	Application findTopByContactContactIdOrderByDateCreatedDesc(Long contactId);

}
