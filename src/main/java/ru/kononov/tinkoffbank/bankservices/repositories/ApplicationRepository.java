package ru.kononov.tinkoffbank.bankservices.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.kononov.tinkoffbank.bankservices.entities.Application;

/**
 * репозиторий для работы с сущностью заявка
 *
 * @author dkononov
 */
@Repository
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {

    /**
     * получение последней заявки по идентификатору контакта
     *
     * @param contactId идентификатор контакта
     * @return заявка
     */
    Application findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(Long contactId);

}
