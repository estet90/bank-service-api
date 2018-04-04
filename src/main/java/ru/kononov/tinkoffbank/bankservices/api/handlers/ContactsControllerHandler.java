package ru.kononov.tinkoffbank.bankservices.api.handlers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kononov.tinkoffbank.bankservices.api.ContactsController;
import ru.kononov.tinkoffbank.bankservices.api.entities.ApplicationDto;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

/**
 * 
 * @author dkononov
 * 
 * обработка запросов для {@link ContactsController} 
 *
 */
@Service
public class ContactsControllerHandler {

	@Autowired
	private ApplicationService applicationService;

	/**
	 * получение последней заявки данного контакта
	 * 
	 * @param contactId идентификатор контакта
	 * @return последняя заявка контакта
	 * @throws BankServicesException если к заявке не привязан контакт. В данном сервисе воспроизвести невозможно.
	 */
	public Response getLastProductByContactId(Long contactId) throws BankServicesException {
		Application application = applicationService.getLastProductByContactId(contactId);
		if (application == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		ApplicationDto applicationDto = new ApplicationDto(application);
		return Response.ok(applicationDto).build();
	}

}
