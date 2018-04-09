package ru.kononov.tinkoffbank.bankservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * сущность заявка
 * 
 * @author dkononov
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "APPLICATION")
public class Application {

	public Application(Contact contact, String productName) {
		this.contact = contact;
		this.productName = productName;
		this.dateCreated = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@ManyToOne
	@JoinColumn(name = "CONTACT_ID")
	private Contact contact;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "DT_CREATED")
	private Date dateCreated;

}
