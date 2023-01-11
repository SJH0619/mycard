package com.mycard.demo.privacy;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmptyStringToNullConverter implements AttributeConverter<String, String>{
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return "".equals(attribute) ? null : attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
