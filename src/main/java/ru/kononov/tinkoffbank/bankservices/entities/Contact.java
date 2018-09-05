package ru.kononov.tinkoffbank.bankservices.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * сущность контакт
 *
 * @author dkononov
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "CONTACT")
public class Contact {

    public Contact(Long contactId) {
        this.contactId = contactId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CONTACT_ID")
    private Long contactId;

    @OneToMany(mappedBy = "contact")
    private List<Application> applications;

}
