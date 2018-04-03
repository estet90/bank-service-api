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
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

@Component
@Path("/contacts")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Api(value = "/contacts")
public class ContactsController {

	@Autowired
	private ApplicationRepository applicationRepository;

	@GET
	@Path("/{contactId}/products/last")
	@ApiOperation(value = "Найти последнюю заявку по идентификатору клиента")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Успех"),
			@ApiResponse(code = 404, message = "Контакт не найден"),
			@ApiResponse(code = 500, message = "Необработанная ошибка")})
	public Response getLastProductByContactId(
			@ApiParam(value = "Идентификатор контакта", required = true) @PathParam("contactId") long contactId) {
		try {
			Application application = applicationRepository.findTopByContactContactIdOrderByDateCreatedDesc(contactId);
			if (application == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
			ApplicationDto applicationDto = new ApplicationDto(application);
			return Response.ok(applicationDto).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

}
