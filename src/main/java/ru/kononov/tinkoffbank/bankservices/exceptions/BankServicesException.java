package ru.kononov.tinkoffbank.bankservices.exceptions;

/**
 * 
 * @author dkononov
 * 
 * исключение, выбрасываемое при валидации
 *
 */
public class BankServicesException extends Exception {

	private static final long serialVersionUID = 1L;

	public BankServicesException(String message) {
		super(message);
	}

}
