package ru.kononov.tinkoffbank.bankservices.api.providers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.impl.ContainerRequestContextImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dkononov
 * 
 * фильтр HTTP-запросов
 *
 */
@Component
@Provider
@PropertySource("classpath:config.yml")
public class RestResponseFilter implements ContainerResponseFilter {

	@Value("${swagger.suffix}")
	private String swaggerSuffix;

	/**
     * При ответе со статусом 404 формируется сообщение со ссылкой на документацию
     *
     *
     * @param requestContext контекст запроса
     * @param responseContext контекст ответа
     * @throws IOException при ошибках ввода/вывода
     */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		StatusType status = responseContext.getStatusInfo();
		if (status.getStatusCode() == Status.NOT_FOUND.getStatusCode()) {
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
		String contextPath = (String) message.get(Message.BASE_PATH);
		// http://host:port/bank-services/api/services
		String apiInfoUrl = new StringBuilder(httpBasePath).append(contextPath).append(swaggerSuffix).toString();
		String apiInfoMessage = new StringBuilder("Ссылка на документацию: ").append(apiInfoUrl).toString();
		HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
		response.sendError(Status.NOT_FOUND.getStatusCode(), apiInfoMessage);
	}

}
