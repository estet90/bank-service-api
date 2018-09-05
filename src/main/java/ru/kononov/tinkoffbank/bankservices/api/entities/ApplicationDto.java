package ru.kononov.tinkoffbank.bankservices.api.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.entities.Contact;
import ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.util.Objects.isNull;
import static ru.kononov.tinkoffbank.bankservices.exceptions.BankServicesException.*;

/**
 * класс-обёртка для {@link Application}
 * <p>
 * используется для отображения данных в форматах JSON/XML
 * добавлено отображение поля {@link Contact#getContactId()}
 *
 * @author dkononov
 */
@NoArgsConstructor
@Setter
@XmlRootElement(name = "APPLICATION")
public class ApplicationDto {

    @Getter(onMethod_ = {
            @XmlElement(name = "APPLICATION_ID"),
            @JsonProperty("APPLICATION_ID")
    })
    private Long applicationId;
    @Getter(onMethod_ = {
            @XmlElement(name = "CONTACT_ID"),
            @JsonProperty("CONTACT_ID")
    })
    private Long contactId;
    @Getter(onMethod_ = {
            @XmlElement(name = "PRODUCT_NAME"),
            @JsonProperty("PRODUCT_NAME")
    })
    private String productName;
    @Getter(onMethod_ = {
            @XmlElement(name = "DT_CREATED"),
            @JsonProperty("DT_CREATED"),
            @XmlJavaTypeAdapter(DateTimeAdapter.class),
            @JsonFormat(shape = STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    })
    private Date dateCreated;

    /**
     * @param application заявка
     * @throws BankServicesException если в конструктор передан null или заявка не содержит контакт
     */
    public ApplicationDto(Application application) throws BankServicesException {
        if (isNull(application)) {
            throw new BankServicesException(MESSAGE_TRANSMITTED_EMPTY_APPLICATION);
        }
        if (isNull(application.getContact())) {
            throw new BankServicesException(MESSAGE_CONTACT_IS_NOT_TIED);
        }
        this.applicationId = application.getApplicationId();
        this.contactId = application.getContact().getContactId();
        this.dateCreated = application.getDateCreated();
        this.productName = application.getProductName();
    }

    /**
     * получение объекта типа {@link Application}
     *
     * @return заявка
     * @throws BankServicesException если идентификатор заявки или контакта пустой
     */
    @XmlTransient
    public Application getApplication() throws BankServicesException {
        if (isNull(this.applicationId)) {
            throw new BankServicesException(MESSAGE_RECEIVED_EMPTY_APPLICATION);
        }
        if (isNull(this.contactId)) {
            throw new BankServicesException(MESSAGE_CONTACT_IS_NOT_TIED);
        }
        Contact contact = new Contact(this.contactId);
        return new Application(this.applicationId, contact, this.productName, this.dateCreated);
    }

}
