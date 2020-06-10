package jeldwen.common.convert.spring;

import org.springframework.core.convert.converter.Converter;

import jeldwen.common.type.CustomDate;

public class CustomDateConverter implements Converter<String, CustomDate> {
	
	@Override
	public CustomDate convert(String source) {
		return CustomDate.fromString(source);
	}
	
}