package jeldwen.common.convert.hibernate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jeldwen.common.type.CustomDate;

@Converter
public class CustomDateAttributeConverter implements AttributeConverter<CustomDate, String> {
	
	@Override
	public String convertToDatabaseColumn(CustomDate customDate) {
		return CustomDate.safeToString(customDate);
	}
	
	@Override
	public CustomDate convertToEntityAttribute(String string) {
		return CustomDate.fromString(string);
	}
	
}