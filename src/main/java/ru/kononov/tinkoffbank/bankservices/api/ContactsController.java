package ru.kononov.tinkoffbank.bankservices.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ru.kononov.tinkoffbank.bankservices.api.handlers.ContactsControllerHandler;

@Component
@Path("/contacts")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Api(value = "/contacts")
public class ContactsController {

	@Autowired
	private ContactsControllerHandler contactsControllerHandler;

	@GET
	@Path("/{contactId}/products/last")
	@ApiOperation(value = "Найти последнюю заявку по идентификатору клиента")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Успех"),
			@ApiResponse(code = 404, message = "Контакт не найден"),
			@ApiResponse(code = 500, message = "Ошибка на стороне сервера") })
	public Response getLastProductByContactId(@Context HttpServletRequest request,
			@ApiParam(value = "Идентификатор контакта", required = true) @PathParam("contactId") long contactId) {
		return contactsControllerHandler.getLastProductByContactId(request, contactId);
	}

}
