package ru.kononov.tinkoffbank.bankservices.api.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * @author dkononov
 * конвертор для отображения даты в формате dd.MM.yyyy HH:mm:ss.SSS в XML/JSON
 *
 */
public class DateTimeAdapter extends XmlAdapter<String, Date> {

	private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");

	@Override
	public Date unmarshal(String xml) throws Exception {
		return dateFormat.parse(xml);
	}

	@Override
	public String marshal(Date date) throws Exception {
		return dateFormat.format(date);
	}

}
