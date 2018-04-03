package ru.kononov.tinkoffbank.bankservices.api.handlers;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.impl.HttpServletRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ru.kononov.tinkoffbank.bankservices.api.entities.ApplicationDto;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.repositories.ApplicationRepository;

@Service
@PropertySource("classpath:config.yml")
public class ContactsControllerHandler {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Value("${swagger.suffix}")
	private String swaggerSuffix;

	public Response getLastProductByContactId(HttpServletRequest request, Long contactId) {
		try {
			Application application = applicationRepository.findTopByContactContactIdOrderByDateCreatedDesc(contactId);
			if (application == null) {
				String redirectUrl = getRedirectUrl((HttpServletRequestFilter)request);
				return Response.seeOther(new URI(redirectUrl)).build();
			}
			ApplicationDto applicationDto = new ApplicationDto(application);
			return Response.ok(applicationDto).build();
		} catch (Exception e) {
			return Response.serverError().entity(e).build();
		}
	}

	private String getRedirectUrl(HttpServletRequestFilter request) {
		String enpointAddress = (String) request.getAttribute("org.apache.cxf.transport.endpoint.address");
		String redirectUrl = new StringBuilder(enpointAddress).append(swaggerSuffix).toString();
		return redirectUrl;
	}

}
