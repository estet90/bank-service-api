package ru.kononov.tinkoffbank.bankservices.api.config;

import org.apache.cxf.jaxrs.impl.ContainerRequestContextImpl;
import org.apache.cxf.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.apache.cxf.message.Message.BASE_PATH;
import static org.apache.cxf.transport.http.AbstractHTTPDestination.HTTP_RESPONSE;

/**
 * фильтр HTTP-запросов
 *
 * @author dkononov
 */
@Component
@Provider
public class RestResponseFilter implements ContainerResponseFilter {

    @Value("${swagger.suffix}")
    private String swaggerSuffix;

    /**
     * При ответе со статусом 404 формируется сообщение со ссылкой на документацию
     *
     * @param requestContext  контекст запроса
     * @param responseContext контекст ответа
     * @throws IOException при ошибках ввода/вывода
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        StatusType status = responseContext.getStatusInfo();
        if (status.getStatusCode() == NOT_FOUND.getStatusCode()) {
            Message message = ((ContainerRequestContextImpl) requestContext).getMessage();
            sendError(message);
        }
    }

    /**
     * Формирование сообщения со ссылкой на документацию
     *
     * @param message контекст запроса
     * @throws IOException при ошибках ввода/вывода
     */
    private void sendError(Message message) throws IOException {
        // получаемое из контекста значение http://host:port
        String httpBasePath = (String) message.get("http.base.path");
        // получаемое из контекста значение /bank-services/api
        String contextPath = (String) message.get(BASE_PATH);
        // http://host:port/bank-services/api/services
        String apiInfoUrl = httpBasePath + contextPath + swaggerSuffix;
        String apiInfoMessage = "Ссылка на документацию: " + apiInfoUrl;
        HttpServletResponse response = (HttpServletResponse) message.get(HTTP_RESPONSE);
        response.sendError(NOT_FOUND.getStatusCode(), apiInfoMessage);
    }

}
