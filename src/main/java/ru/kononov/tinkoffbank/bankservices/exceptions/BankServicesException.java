package ru.kononov.tinkoffbank.bankservices.exceptions;

/**
 * исключение, выбрасываемое при валидации
 *
 * @author dkononov
 */
public class BankServicesException extends Exception {

    public static final String MESSAGE_TRANSMITTED_EMPTY_APPLICATION = "Передан пустой объект типа \"Заявка\"";
    public static final String MESSAGE_RECEIVED_EMPTY_APPLICATION = "Получен пустой объект типа \"Заявка\"";
    public static final String MESSAGE_CONTACT_IS_NOT_TIED = "К заявке не привязан контакт";

    private static final long serialVersionUID = 1L;

    public BankServicesException(String message) {
        super(message);
    }

}
