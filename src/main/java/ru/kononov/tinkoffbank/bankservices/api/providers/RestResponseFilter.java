package ru.kononov.tinkoffbank.bankservices.api.providers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.impl.ContainerRequestContextImpl;
import org.apache.cxf.jaxrs.impl.ContainerResponseContextImpl;
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
     * редирект для запросов со статусом 404 на страницу http://host:port/bank-services/api/services
     *
     * @param requestContext контекст запроса
     * @param responseContext контекст ответа
     * @throws IOException при ошибках ввода/вывода
     */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		int status = ((ContainerResponseContextImpl) responseContext).getStatus();
		if (status == Status.NOT_FOUND.getStatusCode()) {
			Message message = ((ContainerRequestContextImpl) requestContext).getMessage();
			redirect(message);
		}
	}

	/**
	 * редирект на страницу http://host:port/bank-services/api/services
	 * 
	 * @param message контекст запроса
	 * @throws IOException при ошибках ввода/вывода
	 */
	private void redirect(Message message) throws IOException {
		// получаемое из контекста значение http://host:port
		String httpBasePath = (String) message.get("http.base.path");
		// получаемое из контекста значение /bank-services/api
		String contextPath = (String) message.get(Message.BASE_PATH);
		String redirectUrl = new StringBuilder(httpBasePath).append(contextPath).append(swaggerSuffix).toString();
		HttpServletResponse responseFacade = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
		responseFacade.sendRedirect(redirectUrl);
	}

}
