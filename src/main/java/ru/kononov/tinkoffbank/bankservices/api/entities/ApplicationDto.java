package ru.kononov.tinkoffbank.bankservices.api.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.NoArgsConstructor;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;

/**
 * 
 * @author dkononov
 * 
 * класс-обёртка для {@link Application}
 * <p>
 * используется для отображения данных в форматах JSON/XML
 * добавлено отображение поля {@link Contact#getContactId()}
 * 
 *
 */
@NoArgsConstructor
@XmlRootElement(name = "APPLICATION")
public class ApplicationDto {

	Application application;

	/**
	 * 
	 * @param application заявка
	 * @throws BankServicesException если в конструктор передан null или заявка не содержит контакт
	 * 
	 */
	public ApplicationDto(Application application) throws BankServicesException {
		if (application == null) {
			throw new BankServicesException("Передан пустой объект типа \"Заявка\"");
		}
		if (application.getContact() == null) {
			throw new BankServicesException("К заявке не привязан контакт");
		}
		this.application = application;
	}

	@XmlElement(name = "APPLICATION_ID")
	public Long getApplicationId() {
		return application.getApplicationId();
	}

	@XmlElement(name = "CONTACT_ID")
	public Long getContactId() {
		return application.getContact().getContactId();
	}

	@XmlElement(name = "DT_CREATED")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	public Date getDateCreated() {
		return application.getDateCreated();
	}

	@XmlElement(name = "PRODUCT_NAME")
	public String getProductName() {
		return application.getProductName();
	}

}
