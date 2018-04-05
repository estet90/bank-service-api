package ru.kononov.tinkoffbank.bankservices.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author dkononov
 * 
 * сущность контакт
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CONTACT")
public class Contact {

	public Contact(Long contactId) {
		this.contactId = contactId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTACT_ID")
	private Long contactId;

	@OneToMany(mappedBy = "contact")
	private List<Application> applications;

}
