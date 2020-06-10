package jeldwen.common.convert.hibernate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;

import jeldwen.common.type.SimpleDuration;

@Component
@Converter(autoApply = true)
public class SimpleDurationAttributeConverter implements AttributeConverter<SimpleDuration, String> {

    @Override
    public String convertToDatabaseColumn(SimpleDuration simpleDuration) {
        return SimpleDuration.safeToString(simpleDuration);
    }

    @Override
    public SimpleDuration convertToEntityAttribute(String string) {
        return SimpleDuration.fromString(string);
    }
    
}