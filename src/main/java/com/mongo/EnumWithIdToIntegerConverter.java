package com.mongo;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
public class EnumWithIdToIntegerConverter implements ConditionalConverter, Converter<Enum<?>, Integer> {

    @Override
    public Integer convert(Enum<?> source) {
        return ((WithId) source).getId();
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if(WithId.class.isAssignableFrom(sourceType.getType()) && targetType.getType() == Integer.class) {
            return true;
        }
        return false;
    }
}
