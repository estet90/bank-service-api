package ru.kononov.tinkoffbank.bankservices.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * сущность заявка
 *
 * @author dkononov
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
