package ru.kononov.tinkoffbank.bankservices.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

@Path("/contacts")
@Component
public class ContactsController {

	@Autowired
	private ApplicationRepository applicationRepository;

	@GET
	@Path("/{contactId}/products/last")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Application getLastProductByContactId(@PathParam("contactId") long contactId) {
		return applicationRepository.findTopByContactContactIdOrderByDateCreatedDesc(contactId);
	}

}
