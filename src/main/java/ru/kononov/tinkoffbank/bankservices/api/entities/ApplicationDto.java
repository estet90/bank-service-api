package ru.kononov.tinkoffbank.bankservices.api.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.kononov.tinkoffbank.bankservices.entities.Application;

@Getter
@Setter
@ToString
@NoArgsConstructor
@XmlRootElement(name = "APPLICATION")
public class ApplicationDto {

	@JsonProperty("APPLICATION_ID")
	private Long applicationId;

	@JsonProperty("CONTACT_ID")
	private Long contactId;

	@JsonProperty("PRODUCT_NAME")
	private String productName;

	@JsonProperty("DT_CREATED")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy hh:mm:ss")
	private Date dateCreated;

	public ApplicationDto(Application application) {
		this.applicationId = application.getApplicationId();
		this.contactId = application.getContact().getContactId();
		this.productName = application.getProductName();
		this.dateCreated = application.getDateCreated();
	}

}
