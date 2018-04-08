package ru.kononov.tinkoffbank.bankservices.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ru.kononov.tinkoffbank.bankservices.api.entities.ApplicationDto;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

/**
 * 
 * @author dkononov
 *
 * REST API для получения данных по контактам и их заявкам
 *
 */
@Component
@Path("/contacts")
@Produces({ MediaType.APPLICATION_JSON + "; charset=UTF-8", MediaType.APPLICATION_XML + "; charset=UTF-8"})
@Api(value = "/contacts")
public class ContactsController {

	@Autowired
	private ApplicationService applicationService;

	/**
	 * получение последней заявки данного контакта
	 * 
	 * @param contactId идентификатор контакта
	 * @return последняя заявка контакта
	 * @throws BankServicesException если к заявке не привязан контакт. В данном сервисе воспроизвести невозможно.
	 */
	@GET
	@Path("/{contactId}/applications/last")
	@ApiOperation(value = "Найти последнюю заявку по идентификатору клиента")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Успех"),
			@ApiResponse(code = 404, message = "Контакт не найден"),
			@ApiResponse(code = 500, message = "Ошибка на стороне сервера") })
	public Response getLastProductByContactId(
			@ApiParam(value = "Идентификатор контакта", required = true) @PathParam("contactId") long contactId
			) throws BankServicesException {
		Application application = applicationService.getLastProductByContactId(contactId);
		if (application == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		ApplicationDto applicationDto = new ApplicationDto(application);
		return Response.ok(applicationDto).build();
	}

}
