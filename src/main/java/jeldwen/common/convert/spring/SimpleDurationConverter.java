package jeldwen.common.convert.spring;

import org.springframework.core.convert.converter.Converter;

import jeldwen.common.type.SimpleDuration;

public class SimpleDurationConverter implements Converter<String, SimpleDuration> {
	
	@Override
	public SimpleDuration convert(String source) {
		return SimpleDuration.fromString(source);
	}
	
}