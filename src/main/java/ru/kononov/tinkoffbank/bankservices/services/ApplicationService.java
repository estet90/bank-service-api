package ru.kononov.tinkoffbank.bankservices.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

/**
 * обвязка над {@link ApplicationRepository}
 *
 * @author dkononov
 */
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    /**
     * получение последней заявки по идентификатору контакта
     *
     * @param contactId идентификатор контакта
     * @return заявка
     */
    public Application getLastProductByContactId(Long contactId) {
        return applicationRepository.findTopByContactContactIdOrderByDateCreatedDescApplicationIdDesc(contactId);
    }

}
