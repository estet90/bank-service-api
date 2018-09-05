package ru.kononov.tinkoffbank.bankservices.api.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.apache.cxf.jaxrs.utils.JAXRSUtils.convertFaultToResponse;
import static org.apache.cxf.jaxrs.utils.JAXRSUtils.getCurrentMessage;

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
        return convertFaultToResponse(exception.getCause(), getCurrentMessage());
    }

}
