package ru.kononov.tinkoffbank.bankservices.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID")
    private Long contactId;

    @OneToMany(mappedBy = "contact")
    private List<Application> applications;

}
