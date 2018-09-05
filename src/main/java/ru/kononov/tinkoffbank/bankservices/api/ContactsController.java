package ru.kononov.tinkoffbank.bankservices.api;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kononov.tinkoffbank.bankservices.api.entities.ApplicationDto;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

/**
 * REST API для получения данных по контактам и их заявкам
 *
 * @author dkononov
 */
@Component
@Path("/contacts")
@Produces({"application/json; charset=UTF-8", "application/xml; charset=UTF-8"})
@Api(value = "/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ApplicationService applicationService;

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
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успех"),
            @ApiResponse(code = 404, message = "Контакт не найден"),
            @ApiResponse(code = 500, message = "Ошибка на стороне сервера")})
    public Response getLastProductByContactId(
            @ApiParam(value = "Идентификатор контакта", required = true) @PathParam("contactId") long contactId
    ) throws BankServicesException {
        Application application = applicationService.getLastProductByContactId(contactId);
        if (application == null) {
            return status(NOT_FOUND).build();
        }
        ApplicationDto applicationDto = new ApplicationDto(application);
        return ok(applicationDto).build();
    }

}
