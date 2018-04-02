package ru.kononov.tinkoffbank.bankservices.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ru.kononov.tinkoffbank.bankservices.entities.Application;

@Repository
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {

	Application findTopByContactContactIdOrderByDateCreatedDesc(Long contactId);

}
