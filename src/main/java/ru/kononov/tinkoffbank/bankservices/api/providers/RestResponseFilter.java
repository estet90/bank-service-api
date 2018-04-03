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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Provider
@PropertySource("classpath:config.yml")
public class RestResponseFilter implements ContainerResponseFilter {

	@Value("${swagger.suffix}")
	private String swaggerSuffix;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		int status = ((ContainerResponseContextImpl) responseContext).getStatus();
		if (status == Status.NOT_FOUND.getStatusCode()) {
			Message message = ((ContainerRequestContextImpl) requestContext).getMessage();
			redirect(message);
		}
	}

	private void redirect(Message message) throws IOException {
		String httpBasePath = (String) message.get("http.base.path");
		String contextPath = (String) message.get("org.apache.cxf.message.Message.BASE_PATH");
		String redirectUrl = new StringBuilder(httpBasePath).append(contextPath).append(swaggerSuffix).toString();
		HttpServletResponse responseFacade = (HttpServletResponse) message.get("HTTP.RESPONSE");
		responseFacade.sendRedirect(redirectUrl);
	}

}
