package ru.kononov.tinkoffbank.bankservices.api.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;

/**
 * 
 * класс-обёртка для {@link Application}
 * <p>
 * используется для отображения данных в форматах JSON/XML
 * добавлено отображение поля {@link Contact#getContactId()}
 * 
 * @author dkononov
 *
 */
@NoArgsConstructor
@Setter
@XmlRootElement(name = "APPLICATION")
public class ApplicationDto {

	private Long applicationId;

	private Long contactId;

	private String productName;

	private Date dateCreated;

	/**
	 * 
	 * @param application заявка
	 * @throws BankServicesException если в конструктор передан null или заявка не содержит контакт
	 * 
	 */
	public ApplicationDto(Application application) throws BankServicesException {
		if (application == null) {
			throw new BankServicesException(BankServicesException.MESSAGE_TRANSMITTED_EMPTY_APPLICATION);
		}
		if (application.getContact() == null) {
			throw new BankServicesException(BankServicesException.MESSAGE_CONTACT_IS_NOT_TIED);
		}
		this.applicationId = application.getApplicationId();
		this.contactId = application.getContact().getContactId();
		this.dateCreated = application.getDateCreated();
		this.productName = application.getProductName();
	}

	@XmlElement(name = "APPLICATION_ID")
	@JsonProperty("APPLICATION_ID")
	public Long getApplicationId() {
		return this.applicationId;
	}

	@XmlElement(name = "CONTACT_ID")
	@JsonProperty("CONTACT_ID")
	public Long getContactId() {
		return this.contactId;
	}

	@XmlElement(name = "DT_CREATED")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@JsonProperty("DT_CREATED")
	@JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
	public Date getDateCreated() {
		return this.dateCreated;
	}

	@XmlElement(name = "PRODUCT_NAME")
	@JsonProperty("PRODUCT_NAME")
	public String getProductName() {
		return this.productName;
	}

	/**
	 * получение объекта типа {@link Application}
	 * 
	 * @return заявка
	 * @throws BankServicesException если идентификатор заявки или контакта пустой
	 */
	@XmlTransient
	public Application getApplication() throws BankServicesException {
		if (this.applicationId == null) {
			throw new BankServicesException(BankServicesException.MESSAGE_RECEIVED_EMPTY_APPLICATION);
		}
		if (this.contactId == null) {
			throw new BankServicesException(BankServicesException.MESSAGE_CONTACT_IS_NOT_TIED);
		}
		Contact contact = new Contact(this.contactId);
		Application application = new Application(this.applicationId, contact, this.productName, this.dateCreated);
		return application;
	}

}
