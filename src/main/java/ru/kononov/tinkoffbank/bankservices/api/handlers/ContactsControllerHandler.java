package ru.kononov.tinkoffbank.bankservices.api.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kononov.tinkoffbank.bankservices.api.entities.ApplicationDto;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

@Service
public class ContactsControllerHandler {

	@Autowired
	private ApplicationService applicationService;

	public Response getLastProductByContactId(HttpServletRequest request, Long contactId) {
		try {
			Application application = applicationService.getLastProductByContactId(contactId);
			if (application == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
			ApplicationDto applicationDto = new ApplicationDto(application);
			return Response.ok(applicationDto).build();
		} catch (Exception e) {
			return Response.serverError().entity(e).build();
		}
	}

}
