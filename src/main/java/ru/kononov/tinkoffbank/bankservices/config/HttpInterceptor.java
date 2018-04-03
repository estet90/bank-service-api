package ru.kononov.tinkoffbank.bankservices.config;


import javax.ws.rs.core.Response.Status;

import org.apache.cxf.annotations.Provider;
import org.apache.cxf.annotations.Provider.Type;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Provider(value = Type.OutInterceptor)
@Log4j2
public class HttpInterceptor extends AbstractPhaseInterceptor<Message> {

	public HttpInterceptor() {
		super(Phase.SEND);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		int status = (int) message.get(Message.RESPONSE_CODE);
		if (status != Status.OK.getStatusCode()) {
			log.info("JAXRS response status code is {}", message.get(Message.RESPONSE_CODE));
		}
	}

}
