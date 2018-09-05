package ru.kononov.tinkoffbank.bankservices.api.config;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * обработчик исключения {@link InvalidDataAccessResourceUsageException}
 *
 * @author dkononov
 */
@Provider
@Component
@Log4j2
public class InvalidDataAccessResourceUsageExceptionMapper implements ExceptionMapper<InvalidDataAccessResourceUsageException> {

    /**
     * формируется ответ вида
     * {
     * "timestamp": "2018-04-04T13:46:08.774+0000",
     * "status": 500,
     * "error": "Internal Server Error",
     * "message": "сообщение об ошибке",
     * "path": "/bank-services/api/contacts/3/products/last"
     * }
     *
     * @param exception вид исключения, которое должно перехватываться
     * @return ответ, содержащий информацию об ошибке
     */
    @Override
    public Response toResponse(InvalidDataAccessResourceUsageException exception) {
        log.error(exception);
        Message message = JAXRSUtils.getCurrentMessage();
        Response response = JAXRSUtils.convertFaultToResponse(exception.getCause(), message);
        return response;
    }

}
